package projects.sanders87.models.messages;

import java.awt.Color;

import projects.sanders87.models.nodes.S87AbstractNode;
import sinalgo.nodes.messages.Message;

public class S87RequestMessage extends S87AbstractMessage {

	public S87RequestMessage(S87AbstractNode sender, int timestamp) {
		super(sender, timestamp);
		type = "Request";
	}

	@Override
	public Message clone() {
		return new S87RequestMessage(sender, timestamp);
	}
	
	@Override
	public Color getEnvelopeColor() {
		return Color.CYAN;
	}
	
	@Override
	public String toString() {
		return "RequestMessage(" + super.toString() + ")";
	}
	
}
