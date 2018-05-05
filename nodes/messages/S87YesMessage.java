package projects.sanders87.nodes.messages;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.nodes.messages.Message;

public class S87YesMessage extends S87Message {

	public S87YesMessage(S87Node sender) {
		super(sender);
	}

	@Override
	public Message clone() {
		return new S87YesMessage(this.sender);
	}
	
}
