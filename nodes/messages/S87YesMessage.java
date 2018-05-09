package projects.sanders87.nodes.messages;

import java.awt.Color;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.nodes.messages.Message;

public class S87YesMessage extends S87Message {

	public S87YesMessage(S87Node sender) {
		super(sender);
		type = "Yes";
	}

	@Override
	public Message clone() {
		return new S87YesMessage(this.sender);
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
