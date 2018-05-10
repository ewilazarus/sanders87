package projects.sanders87.models.messages;

import java.awt.Color;

import projects.sanders87.models.nodes.S87AbstractNode;
import sinalgo.nodes.messages.Message;

public class S87RelinquishMessage extends S87AbstractMessage {

	public S87RelinquishMessage(S87AbstractNode sender, int timestamp) {
		super(sender, timestamp);
		type = "Relinquish";
	}

	@Override
	public Message clone() {
		return new S87RelinquishMessage(sender, timestamp);
	}

	@Override
	public Color getEnvelopeColor() {
		return Color.PINK;
	}
	
	@Override
	public String toString() {
		return "RelinquishMessage(" + super.toString() + ")";
	}
	
}
