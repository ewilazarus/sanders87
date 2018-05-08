package projects.sanders87.nodes.implementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.PriorityQueue;
import java.util.Random;

import projects.sanders87.nodes.messages.S87InquireMessage;
import projects.sanders87.nodes.messages.S87Message;
import projects.sanders87.nodes.messages.S87ReleaseMessage;
import projects.sanders87.nodes.messages.S87RelinquishMessage;
import projects.sanders87.nodes.messages.S87RequestMessage;
import projects.sanders87.nodes.messages.S87TimestampedMessage;
import projects.sanders87.nodes.messages.S87YesMessage;
import projects.sanders87.nodes.states.S87State;
import projects.sanders87.nodes.timers.S87Timer;
import sinalgo.configuration.Configuration;
import sinalgo.configuration.CorruptConfigurationEntryException;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import sinalgo.tools.statistics.Distribution;

public class S87Node extends Node {
	
	public int clock = 0;
	public S87State state;
	
	public int votes = 0;
	public int timestamp = 0;
	
	public boolean hasVoted = false;
	public boolean hasInquired = false;
	
	public S87Node candidate;
	public int candidateTimestamp;
	
	public PriorityQueue<S87TimestampedMessage> deferedQueue;
	private S87MessageHandler handler;
	
	@Override
	public void init() {
		state = S87State.NOT_IN_CS;
		handler = new S87MessageHandler(this);
		deferedQueue = new PriorityQueue<S87TimestampedMessage>();
	}
	
	@Override
	public void preStep() {
		Random distribution = Distribution.getRandom();
		double value = distribution.nextDouble();
		
		try {
			if (state == S87State.NOT_IN_CS && value <= Configuration.getDoubleParameter("CriticalSection/EnterThreshold")) {
				waitForCS();
			}
		} catch (CorruptConfigurationEntryException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void postStep() {
		clock++;
	}
	
	@Override
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		super.drawNodeAsDiskWithText(g, pt, highlight, this.toString(), 20, Color.WHITE);
	}
	
	@Override
	public Color getColor() {
		switch (state) {
		case NOT_IN_CS: return Color.RED;
		case WAITING: return Color.ORANGE;
		case IN_CS: return Color.GREEN;
		default: return Color.BLACK;
		}
	}
	
	@Override
	public String toString() {
		return String.format("%03d", this.ID);
	}
	
	@Override
	public void checkRequirements() throws WrongConfigurationException {}
	
	@Override
	public void neighborhoodChange() {}
	
	@Override
	public void handleMessages(Inbox inbox) {
		while(inbox.hasNext()) {
			S87Message message = (S87Message) inbox.next();

			if (message instanceof S87InquireMessage) {
				handler.handle((S87InquireMessage) message);
			} else if (message instanceof S87ReleaseMessage) {
				handler.handle((S87ReleaseMessage) message);
			} else if (message instanceof S87RelinquishMessage) {
				handler.handle((S87RelinquishMessage) message);
			} else if (message instanceof S87RequestMessage) {
				handler.handle((S87RequestMessage) message);
			} else if (message instanceof S87YesMessage) {
				handler.handle((S87YesMessage) message);
			}
		}
	}
	
	public boolean shouldInquire(S87RequestMessage message) {
		if (hasInquired) return false;
		return message.timestamp < candidateTimestamp ||
			   message.timestamp == candidateTimestamp && message.sender.ID < candidate.ID;
	}
		
	public void send(S87Message message, S87Node node) {
		super.send(message, node);
	}
	
	public void vote() {
		S87TimestampedMessage message = deferedQueue.poll();
		if (message != null) {
			send(new S87YesMessage(this), message.sender);
			candidate = message.sender;
			candidateTimestamp = message.timestamp;
		} else {
			hasVoted = false;
		}
		hasInquired = false;
	}
	
	public void requestVotes() {
		timestamp = clock;
		broadcast(new S87RequestMessage(this, timestamp));
	}
	
	public void releaseVotes() {
		votes = 0;
		broadcast(new S87ReleaseMessage(this));
	}
	
	public void broadcast(S87Message message) {
		super.broadcast(message);
	}
	
	public void waitForCS() {
		state = S87State.WAITING;
		requestVotes();
	}
	
	public void enterCS() {
		state = S87State.IN_CS;
		new S87Timer().startRelative(3, this);  // TODO: add random time
	}
	
	public void leaveCS() {
		state = S87State.NOT_IN_CS;
		releaseVotes();
	}
	
	public boolean hasAllVotes() {
		return votes == outgoingConnections.size();
	}
}
