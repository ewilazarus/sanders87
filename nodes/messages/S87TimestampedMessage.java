package projects.sanders87.nodes.messages;

import projects.sanders87.nodes.implementations.S87Node;

public abstract class S87TimestampedMessage extends S87Message implements Comparable<S87TimestampedMessage> {
	
	public int timestamp;
	
	public S87TimestampedMessage(S87Node sender, int timestamp) {
		super(sender);
		this.timestamp = timestamp;
	}
	
	@Override
	public int compareTo(S87TimestampedMessage other) {
		int timestampComparsion = Integer.compare(timestamp, other.timestamp);
		return timestampComparsion != 0
			? timestampComparsion 
			: Integer.compare(sender.ID, other.sender.ID); 
	}
	
	@Override
	public String toString() {
		return super.toString() + ", timestamp=" + timestamp; 
	}
				
}
