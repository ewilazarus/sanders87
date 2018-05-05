package projects.sanders87.nodes.messages;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.nodes.messages.Message;

public class S87InquireMessage extends S87TimestampedMessage {

	public S87InquireMessage(S87Node sender, int timestamp) {
		super(sender, timestamp);
	}
	
	@Override
	public Message clone() {
		return new S87InquireMessage(this.sender, this.timestamp);
	}

}
