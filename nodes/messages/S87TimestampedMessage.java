package projects.sanders87.nodes.messages;

public abstract class S87TimestampedMessage extends S87Message {
	
	protected int timestamp;
	
	public S87TimestampedMessage(int timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getTimestamp() {
		return timestamp;
	}
	
}
