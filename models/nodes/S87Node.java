package projects.sanders87.models.nodes;

import projects.sanders87.models.messages.S87AbstractMessage;
import projects.sanders87.models.messages.S87InquireMessage;
import projects.sanders87.models.messages.S87ReleaseMessage;
import projects.sanders87.models.messages.S87RelinquishMessage;
import projects.sanders87.models.messages.S87RequestMessage;
import projects.sanders87.models.messages.S87YesMessage;

public class S87Node extends S87AbstractNode {

	@Override
	protected void handle(S87InquireMessage message) {
		if (getCondition() == S87NodeCondition.WAITING && message.timestamp == state.timestamp) {
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
			if (message.compareTo(state.candidate) < 0 && !state.hasInquired) {
				send(new S87InquireMessage(this, state.candidate.timestamp), state.candidate.node);
				state.hasInquired = true;
			}
		} else {
			vote(message.sender, message.timestamp);			
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
		setCondition(S87NodeCondition.WAITING);
		requestVotes();
	}
	
	private void enterCS() {
		setCondition(S87NodeCondition.IN_CS);
		simulateResourceConsumption();
	}
	
	public void leaveCS() {
		setCondition(S87NodeCondition.NOT_IN_CS);
		releaseVotes();
	}
	
	private void vote() {
		if (!deferedQueue.isEmpty()) {
			S87AbstractMessage message = deferedQueue.poll();
			vote(message.sender, message.timestamp);
		} else {
			state.hasVoted = false;
			state.hasInquired = false;
		}
	}
	
	private void vote(S87AbstractNode node, int timestamp) {
		send(new S87YesMessage(this, currentTimestamp), node);
		state.hasVoted = true;
		state.hasInquired = false;
		state.updateCandidate(node, timestamp);
	}
		
	private void requestVotes() {
		broadcast(new S87RequestMessage(this, state.getUpdatedTimestamp()));
	}
	
	private void releaseVotes() {
		state.votesReceived = 0;
		broadcast(new S87ReleaseMessage(this, currentTimestamp));
	}
}
