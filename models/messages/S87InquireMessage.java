package projects.sanders87.models.messages;

import java.awt.Color;

import projects.sanders87.models.nodes.S87AbstractNode;
import sinalgo.nodes.messages.Message;

public class S87InquireMessage extends S87AbstractMessage {

	public S87InquireMessage(S87AbstractNode sender, int timestamp) {
		super(sender, timestamp);
		type = "Inquire";
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
