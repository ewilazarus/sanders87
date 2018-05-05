package projects.sanders87.nodes.messages;

import sinalgo.nodes.messages.Message;

public class S87YesMessage extends S87Message {

	@Override
	public Message clone() {
		return new S87YesMessage();
	}
	
}
