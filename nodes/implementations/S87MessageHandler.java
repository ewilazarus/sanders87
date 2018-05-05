package projects.sanders87.nodes.implementations;

import projects.sanders87.nodes.messages.S87InquireMessage;
import projects.sanders87.nodes.messages.S87ReleaseMessage;
import projects.sanders87.nodes.messages.S87RelinquishMessage;
import projects.sanders87.nodes.messages.S87RequestMessage;
import projects.sanders87.nodes.messages.S87YesMessage;
import projects.sanders87.nodes.states.S87State;

public class S87MessageHandler {
	
	private S87Node receiver;
		
	public S87MessageHandler(S87Node receiver) {
		this.receiver = receiver;
	}
	
	public void handle(S87InquireMessage message) {
		if (receiver.state == S87State.WAITING && message.timestamp == receiver.timestamp) {
			receiver.send(new S87RelinquishMessage(receiver, receiver.timestamp), message.sender);
			receiver.votes--;
		}
	}
	
	public void handle(S87ReleaseMessage message) {
		receiver.vote();
	}
	
	public void handle(S87RelinquishMessage message) {
		receiver.deferedQueue.add(message);
		receiver.vote();
	}
	
	public void handle(S87RequestMessage message) {
		if (receiver.hasVoted) {
			receiver.deferedQueue.add(message);
			
			if (receiver.shouldInquire(message)) {
				receiver.send(new S87InquireMessage(receiver, receiver.candidateTimestamp), receiver.candidate);
				receiver.hasInquired = true;
			}
		} else {
			receiver.send(new S87YesMessage(receiver), message.sender);
			receiver.hasVoted = true;
			receiver.candidate = message.sender;
			receiver.candidateTimestamp = message.timestamp;			
		}
	}
	
	public void handle(S87YesMessage message) {
		receiver.votes++;
		if (receiver.hasAllVotes()) {
			receiver.enterCS();
		}
	}
	
}
