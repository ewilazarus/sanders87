package projects.sanders87.nodes.messages;

import projects.sanders87.nodes.implementations.S87Node;

public abstract class S87TimestampedMessage extends S87Message {
	
	public int timestamp;
	
	public S87TimestampedMessage(S87Node sender, int timestamp) {
		super(sender);
		this.timestamp = timestamp;
	}
				
}
