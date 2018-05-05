package projects.sanders87.nodes.messages;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.nodes.messages.Message;

public class S87ReleaseMessage extends S87Message {
	
	public S87ReleaseMessage(S87Node sender) {
		super(sender);
	}

	@Override
	public Message clone() {
		return new S87ReleaseMessage(this.sender);
	}

}
