package projects.sanders87.models.messages;

import java.awt.Color;

import projects.sanders87.models.nodes.S87AbstractNode;
import sinalgo.nodes.messages.Message;

public class S87ReleaseMessage extends S87AbstractMessage {
	
	public S87ReleaseMessage(S87AbstractNode sender, int timestamp) {
		super(sender, timestamp);
		type = "Release";
	}

	@Override
	public Message clone() {
		return new S87ReleaseMessage(sender, timestamp);
	}
	
	@Override
	public Color getEnvelopeColor() {
		return Color.MAGENTA;
	}

	@Override
	public String toString() {
		return "ReleaseMessage(" + super.toString() + ")";
	}
	
}
