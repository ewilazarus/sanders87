package projects.sanders87.nodes.messages;

import sinalgo.nodes.messages.Message;

public class S87InquireMessage extends S87TimestampedMessage {

	public S87InquireMessage(int timestamp) {
		super(timestamp);
	}
	
	@Override
	public Message clone() {
		return new S87InquireMessage(this.timestamp);
	}

}
