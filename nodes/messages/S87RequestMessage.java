package projects.sanders87.nodes.messages;

import sinalgo.nodes.messages.Message;

public class S87RequestMessage extends S87TimestampedMessage {

	public S87RequestMessage(int timestamp) {
		super(timestamp);
	}

	@Override
	public Message clone() {
		return new S87RequestMessage(this.timestamp);
	}
	
}
