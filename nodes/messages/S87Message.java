package projects.sanders87.nodes.messages;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.nodes.messages.Message;

public abstract class S87Message extends Message {
	
	public S87Node sender;
	
	public S87Message(S87Node sender) {
		this.sender = sender;
	}
}
