package projects.sanders87.nodes.messages;

import java.awt.Color;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.nodes.messages.Message;

public abstract class S87Message extends Message {
	
	public S87Node sender;
	public String type;
	
	public S87Message(S87Node sender) {
		this.sender = sender;
	}
	
	@Override
	public abstract Color getEnvelopeColor();
	
	@Override
	public String toString() {
		return "sender=" + sender;
	}

}
