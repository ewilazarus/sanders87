package projects.sanders87.models.messages;

import java.awt.Color;

import projects.sanders87.models.S87LogicalTimestampHolder;
import projects.sanders87.models.nodes.S87AbstractNode;
import sinalgo.nodes.messages.Message;

public abstract class S87AbstractMessage extends Message implements S87LogicalTimestampHolder, Comparable<S87LogicalTimestampHolder> {
	
	public S87AbstractNode sender;
	public int timestamp;
	public String type;
	
	public S87AbstractMessage(S87AbstractNode sender, int timestamp) {
		this.sender = sender;
		this.timestamp = timestamp;
	}
	
	@Override
	public abstract Color getEnvelopeColor();
		
	@Override
	public int compareTo(S87LogicalTimestampHolder other) {
		int timestampComparsion = Integer.compare(getTimestamp(), other.getTimestamp());
		return timestampComparsion != 0
			? timestampComparsion 
			: Integer.compare(getTieBreaker(), other.getTieBreaker()); 
	}
	
	@Override
	public String toString() {
		return "sender=" + sender + ", timestamp=" + timestamp;
	}
	
	@Override
	public int getTimestamp() {
		return timestamp;
	}
	
	@Override
	public int getTieBreaker() {
		return sender.ID;
	}
	
}
