package projects.sanders87.nodes.messages;

import java.awt.Color;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.nodes.messages.Message;

public class S87ReleaseMessage extends S87Message {
	
	public S87ReleaseMessage(S87Node sender) {
		super(sender);
		type = "Release";
	}

	@Override
	public Message clone() {
		return new S87ReleaseMessage(this.sender);
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
