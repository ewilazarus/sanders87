package projects.sanders87.nodes.messages;

import java.awt.Color;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.nodes.messages.Message;

public class S87RequestMessage extends S87TimestampedMessage {

	public S87RequestMessage(S87Node sender, int timestamp) {
		super(sender, timestamp);
	}

	@Override
	public Message clone() {
		return new S87RequestMessage(this.sender, this.timestamp);
	}
	
	@Override
	public Color getEnvelopeColor() {
		return Color.CYAN;
	}
	
}
