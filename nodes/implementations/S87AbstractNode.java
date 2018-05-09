package projects.sanders87.nodes.implementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.PriorityQueue;
import java.util.Random;

import projects.sanders87.LogL;
import projects.sanders87.metrics.S87MetricCollector;
import projects.sanders87.nodes.messages.S87InquireMessage;
import projects.sanders87.nodes.messages.S87Message;
import projects.sanders87.nodes.messages.S87ReleaseMessage;
import projects.sanders87.nodes.messages.S87RelinquishMessage;
import projects.sanders87.nodes.messages.S87RequestMessage;
import projects.sanders87.nodes.messages.S87TimestampedMessage;
import projects.sanders87.nodes.messages.S87YesMessage;
import sinalgo.configuration.Configuration;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import sinalgo.tools.logging.Logging;
import sinalgo.tools.statistics.Distribution;

public abstract class S87AbstractNode extends Node {
	
	private static final Random distribution = Distribution.getRandom();
	private static double enterCSThreshold;
	
	static {
		try {
			enterCSThreshold = Configuration.getDoubleParameter("CriticalSection/EnterThreshold");
		} catch (CorruptConfigurationEntryException e) {
			e.printStackTrace();
		}
	}
	
	public enum S87NodeCondition {
		NOT_IN_CS,
		WAITING,
		IN_CS,
	}
		
	public class S87NodeState {
		public int votesReceived = 0;
		public int timestamp = 0;
		public boolean hasVoted = false;
		public boolean hasInquired = false;
		public S87Node candidate;
		public int candidateTimestamp;
		private S87NodeCondition condition;
		
		public boolean hasAllVotes() {
			return votesReceived == outgoingConnections.size();
		}
		
		public void updateCandidate(S87Node candidate, int candidateTimestamp) {
			this.candidate = candidate;
			this.candidateTimestamp = candidateTimestamp;
		}
		
		public int getUpdatedTimestamp() {
			timestamp = executionTimestamp;
			return timestamp;
		}
		
		public boolean shouldInquire(S87RequestMessage message) {
			return !hasInquired &&
				   (message.timestamp < candidateTimestamp || 
				    message.timestamp == candidateTimestamp && message.sender.ID < candidate.ID);
		}
				
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("votesReceived=" + votesReceived);
			sb.append(", ");
			sb.append("timestamp=" + timestamp);
			sb.append(", ");
			sb.append("hasVoted=" + hasVoted);
			sb.append(", ");
			sb.append("hasInquired=" + hasInquired);
			sb.append(", ");
			sb.append("candidate=" + candidate);
			sb.append(", ");
			sb.append("candidateTimestamp=" + candidateTimestamp);
			sb.append(", ");
			sb.append("condition=" + condition.name());
			return "NodeState(" + sb + ")";
		}
	}
	
	private Logging logger = Logging.getLogger("sanders87.log");
	private int executionTimestamp = 0;
	protected final PriorityQueue<S87TimestampedMessage> deferedQueue = new PriorityQueue<>();
	public final S87NodeState state = new S87NodeState();
	public final String label = String.format("%03d", this.ID);
	
	
	@Override
	public void checkRequirements() throws WrongConfigurationException {}
		
	@Override
	public void init() {
		setCondition(S87NodeCondition.NOT_IN_CS);
	}
		
	@Override
	public void preStep() {
		if (state.condition == S87NodeCondition.NOT_IN_CS && generateRandomValue() <= enterCSThreshold) {
			waitForCS();
		}
	}
		
	@Override
	public void handleMessages(Inbox inbox) {
		while(inbox.hasNext()) {
			S87Message message = (S87Message) inbox.next();
			switch (message.type) {
			case "Inquire": handle((S87InquireMessage) message); break;
			case "Release": handle((S87ReleaseMessage) message); break;
			case "Relinquish": handle((S87RelinquishMessage) message); break;
			case "Request": handle((S87RequestMessage) message); break;
			case "Yes": handle((S87YesMessage) message); break;
			}
		}
	}
	
	@Override
	public void postStep() {
		executionTimestamp++;
	}
	
	@Override
	public void neighborhoodChange() {}

	
	@Override
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		super.drawNodeAsDiskWithText(g, pt, highlight, label, 20, Color.WHITE);
	}
	
	@Override
	public Color getColor() {
		switch (state.condition) {
		case NOT_IN_CS: return Color.RED;
		case WAITING: return Color.ORANGE;
		case IN_CS: return Color.GREEN;
		default: return Color.BLACK;
		}
	}
	
	@Override
	public String toString() {
		return "Node(label=" + label + ")";
	}
	
	protected abstract void handle(S87InquireMessage message);
	protected abstract void handle(S87ReleaseMessage message);
	protected abstract void handle(S87RelinquishMessage message);
	protected abstract void handle(S87RequestMessage message);
	protected abstract void handle(S87YesMessage message);
	
	protected abstract void waitForCS();
	
	protected void send(S87Message message, S87Node node) {
		logger.logln(LogL.TRACE_MESSAGES, "SENDING " + message + " TO " + node);
		S87MetricCollector.countMessage(message);
		
		super.send(message, node);
	}
	
	protected void broadcast(S87Message message) {
		logger.logln(LogL.TRACE_MESSAGES, "BROADCASTING " + message);
		S87MetricCollector.countMessages(message, outgoingConnections.size());
		
		super.broadcast(message);
	}
	
	public S87NodeCondition getCondition() {
		return state.condition;
	}
	
	protected void setCondition(S87NodeCondition condition) {
		state.condition = condition;
		S87MetricCollector.countCondition(this);
	}
		
	private double generateRandomValue() {
		return distribution.nextDouble();
	}

}
