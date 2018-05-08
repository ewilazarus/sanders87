package projects.sanders87.nodes.implementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.PriorityQueue;
import java.util.Random;

import projects.sanders87.nodes.messages.S87InquireMessage;
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
		public S87NodeCondition condition = S87NodeCondition.NOT_IN_CS;
		
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
	}
	
	private int executionTimestamp = 0;
	protected final PriorityQueue<S87TimestampedMessage> deferedQueue = new PriorityQueue<>();
	protected final S87NodeState state = new S87NodeState();
	protected final String label = String.format("%03d", this.ID);
	
	@Override
	public void checkRequirements() throws WrongConfigurationException {}
		
	@Override
	public void init() {}
		
	@Override
	public void preStep() {
		if (state.condition == S87NodeCondition.NOT_IN_CS && generateRandomValue() <= enterCSThreshold) {
			waitForCS();
		}
	}
		
	@Override
	public void handleMessages(Inbox inbox) {
		while(inbox.hasNext()) {
			Object message = inbox.next();
			if (message instanceof S87InquireMessage) {
				handle((S87InquireMessage) message);
			} else if (message instanceof S87ReleaseMessage) {
				handle((S87ReleaseMessage) message);
			} else if (message instanceof S87RelinquishMessage) {
				handle((S87RelinquishMessage) message);
			} else if (message instanceof S87RequestMessage) {
				handle((S87RequestMessage) message);
			} else if (message instanceof S87YesMessage) {
				handle((S87YesMessage) message);
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
		return label;  // TODO: improve for logging
	}
	
	protected abstract void handle(S87InquireMessage message);
	protected abstract void handle(S87ReleaseMessage message);
	protected abstract void handle(S87RelinquishMessage message);
	protected abstract void handle(S87RequestMessage message);
	protected abstract void handle(S87YesMessage message);
	
	protected abstract void waitForCS();
		
	private double generateRandomValue() {
		return distribution.nextDouble();
	}

}
