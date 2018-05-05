package projects.sanders87.nodes.messages;

import sinalgo.nodes.messages.Message;

public class S87RelinquishMessage extends S87TimestampedMessage {

	public S87RelinquishMessage(int timestamp) {
		super(timestamp);
	}

	@Override
	public Message clone() {
		return new S87RelinquishMessage(this.timestamp);
	}

}
