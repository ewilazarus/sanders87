package projects.sanders87.models.messages;

import java.awt.Color;

import projects.sanders87.models.nodes.S87AbstractNode;
import sinalgo.nodes.messages.Message;

public class S87YesMessage extends S87AbstractMessage {

	public S87YesMessage(S87AbstractNode sender, int timestamp) {
		super(sender, timestamp);
		type = "Yes";
	}

	@Override
	public Message clone() {
		return new S87YesMessage(sender, timestamp);
	}
	
	@Override
	public Color getEnvelopeColor() {
		return Color.DARK_GRAY;
	}
	
	@Override
	public String toString() {
		return "YesMessage(" + super.toString() + ")";
	}
	
}
