package projects.sanders87.nodes.implementations;

import projects.sanders87.nodes.messages.S87InquireMessage;
import projects.sanders87.nodes.messages.S87ReleaseMessage;
import projects.sanders87.nodes.messages.S87RelinquishMessage;
import projects.sanders87.nodes.messages.S87RequestMessage;
import projects.sanders87.nodes.messages.S87YesMessage;

public class S87MessageHandler {
	
	private S87Node receiver;
	private S87Node sender;
	
	public S87MessageHandler(S87Node receiver, S87Node sender) {
		this.receiver = receiver;
		this.sender = sender;
	}
	
	public void handle(S87InquireMessage message) {
		
	}
	
	public void handle(S87ReleaseMessage message) {
		
	}
	
	public void handle(S87RelinquishMessage message) {
		
	}
	
	public void handle(S87RequestMessage message) {
		
	}
	
	public void handle(S87YesMessage message) {
		receiver.votes++;
		
	}
	
}
