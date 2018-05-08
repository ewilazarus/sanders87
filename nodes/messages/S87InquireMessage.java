package projects.sanders87.nodes.messages;

import java.awt.Color;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.nodes.messages.Message;

public class S87InquireMessage extends S87TimestampedMessage {

	public S87InquireMessage(S87Node sender, int timestamp) {
		super(sender, timestamp);
	}
	
	@Override
	public Message clone() {
		return new S87InquireMessage(sender, timestamp);
	}
	
	@Override
	public Color getEnvelopeColor() {
		return Color.RED;
	}
	
	@Override
	public String toString() {
		return "InquireMessage(" + super.toString() + ")";
	}

}
