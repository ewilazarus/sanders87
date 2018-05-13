package projects.sanders87.models.nodes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.PriorityQueue;
import java.util.Random;

import projects.sanders87.LogL;
import projects.sanders87.metrics.S87MetricCollector;
import projects.sanders87.models.messages.S87InquireMessage;
import projects.sanders87.models.S87LogicalTimestampHolder;
import projects.sanders87.models.messages.S87AbstractMessage;
import projects.sanders87.models.messages.S87ReleaseMessage;
import projects.sanders87.models.messages.S87RelinquishMessage;
import projects.sanders87.models.messages.S87RequestMessage;
import projects.sanders87.models.messages.S87YesMessage;
import sinalgo.configuration.Configuration;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.timers.Timer;
import sinalgo.tools.logging.Logging;
import sinalgo.tools.statistics.Distribution;

public abstract class S87AbstractNode extends Node implements S87LogicalTimestampHolder {
	
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
	
	
	public class S87Candidate implements S87LogicalTimestampHolder {
		public S87AbstractNode node;
		public int timestamp;
		
		@Override
		public int getTimestamp() {
			return timestamp;
		}
		@Override
		public int getTieBreaker() {
			return node.ID;
		}
		
		@Override
		public String toString() {
			return "S87Candidate(node=" + node + ", timestamp=" + timestamp + ")";
		}
	}
	
	public class S87NodeState {
		public int votesReceived = 0;
		public int timestamp = 0;
		public boolean hasVoted = false;
		public boolean hasInquired = false;
		public S87Candidate candidate = new S87Candidate();
		private S87NodeCondition condition;
		
		public boolean hasAllVotes() {
			return votesReceived == outgoingConnections.size();
		}
		
		public void updateCandidate(S87AbstractNode candidate, int candidateTimestamp) {
			this.candidate.node = candidate;
			this.candidate.timestamp = candidateTimestamp;
		}
		
		public int getUpdatedTimestamp() {
			timestamp = currentTimestamp;
			return timestamp;
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
			sb.append("candidateInfo=" + candidate);
			sb.append(", ");
			sb.append("condition=" + condition.name());
			return "NodeState(" + sb + ")";
		}
	}
	
	private class S87Timer extends Timer {

		@Override
		public void fire() {
			((S87Node) this.node).leaveCS();
		}
		
	}
		
	private Logging logger = Logging.getLogger("sanders87.dbg.log");
	protected int currentTimestamp = 0;
	protected final PriorityQueue<S87AbstractMessage> deferedQueue = new PriorityQueue<>();
	public final S87NodeState state = new S87NodeState();
	public final String label = String.format("%02d", this.ID);
		
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
			S87AbstractMessage message = (S87AbstractMessage) inbox.next();
			
			currentTimestamp++;
			if (message.compareTo(this) > 0) {
				currentTimestamp = message.timestamp + 1;
			}
			
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
	public void postStep() {}
	
	@Override
	public void neighborhoodChange() {}

	
	@Override
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		super.drawNodeAsDiskWithText(g, pt, highlight, label, 30, Color.WHITE);
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
	
	@Override
	public int getTimestamp() {
		return currentTimestamp;
	}
	
	@Override
	public int getTieBreaker() {
		return ID;
	}
	
	protected abstract void handle(S87InquireMessage message);
	protected abstract void handle(S87ReleaseMessage message);
	protected abstract void handle(S87RelinquishMessage message);
	protected abstract void handle(S87RequestMessage message);
	protected abstract void handle(S87YesMessage message);
	
	protected abstract void waitForCS();
	
	protected void send(S87AbstractMessage message, S87AbstractNode node) {
		logger.logln(LogL.TRACE_MESSAGES, "SENDING " + message + " TO " + node);
		S87MetricCollector.countMessage(message);
		
		currentTimestamp++;
		super.send(message, node);
	}
	
	protected void broadcast(S87AbstractMessage message) {
		logger.logln(LogL.TRACE_MESSAGES, "BROADCASTING " + message);
		S87MetricCollector.countMessages(message, outgoingConnections.size());
		
		currentTimestamp++;
		super.broadcast(message);
	}
	
	public S87NodeCondition getCondition() {
		return state.condition;
	}
	
	protected void setCondition(S87NodeCondition condition) {
		state.condition = condition;
		S87MetricCollector.countCondition(this);
	}
		
	protected double generateRandomValue() {
		return distribution.nextDouble();
	}
	
	protected void simulateResourceConsumption() {
		new S87Timer().startRelative(generateRandomValue() * 10, this);
	}

}
