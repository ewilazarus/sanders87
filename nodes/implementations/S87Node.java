package projects.sanders87.nodes.implementations;

import projects.sanders87.nodes.messages.S87InquireMessage;
import projects.sanders87.nodes.messages.S87ReleaseMessage;
import projects.sanders87.nodes.messages.S87RelinquishMessage;
import projects.sanders87.nodes.messages.S87RequestMessage;
import projects.sanders87.nodes.messages.S87TimestampedMessage;
import projects.sanders87.nodes.messages.S87YesMessage;
import projects.sanders87.nodes.timers.S87Timer;

public class S87Node extends S87AbstractNode {

	@Override
	protected void handle(S87InquireMessage message) {
		if (state.condition == S87NodeCondition.WAITING && message.timestamp == state.timestamp) {
			send(new S87RelinquishMessage(this, state.timestamp), message.sender);
			state.votesReceived--;
		}
	}
	
	@Override
	protected void handle(S87ReleaseMessage message) {
		vote();
	}
	
	@Override
	protected void handle(S87RelinquishMessage message) {
		deferedQueue.add(message);
		vote();
	}
	
	@Override
	protected void handle(S87RequestMessage message) {
		if (state.hasVoted) {
			deferedQueue.add(message);
			if (state.shouldInquire(message)) {
				send(new S87InquireMessage(this, state.candidateTimestamp), state.candidate);
				state.hasInquired = true;
			}
		} else {
			send(new S87YesMessage(this), message.sender);
			state.hasVoted = true;
			state.updateCandidate(message.sender, message.timestamp);			
		}
	}
	
	@Override
	protected void handle(S87YesMessage message) {
		state.votesReceived++;
		if (state.hasAllVotes()) {
			enterCS();
		}
	}
	
	@Override
	protected void waitForCS() {
		state.condition = S87NodeCondition.WAITING;
		requestVotes();
	}
	
	private void enterCS() {
		state.condition = S87NodeCondition.IN_CS;
		new S87Timer().startRelative(3, this);  // TODO: add random time
	}
	
	public void leaveCS() {
		state.condition = S87NodeCondition.NOT_IN_CS;
		releaseVotes();
	}
	
	public void vote() {
		if (!deferedQueue.isEmpty()) {
			S87TimestampedMessage message = deferedQueue.poll();
			send(new S87YesMessage(this), message.sender);
			state.updateCandidate(message.sender, message.timestamp);
		} else {
			state.hasVoted = false;
		}
		state.hasInquired = false;
	}
	
	private void requestVotes() {
		broadcast(new S87RequestMessage(this, state.getUpdatedTimestamp()));
	}
	
	private void releaseVotes() {
		state.votesReceived = 0;
		broadcast(new S87ReleaseMessage(this));
	}
}
