package projects.sanders87.nodes.implementations;

import java.awt.Color;
import java.awt.Graphics;

import projects.sanders87.nodes.messages.S87InquireMessage;
import projects.sanders87.nodes.messages.S87Message;
import projects.sanders87.nodes.messages.S87ReleaseMessage;
import projects.sanders87.nodes.messages.S87RelinquishMessage;
import projects.sanders87.nodes.messages.S87RequestMessage;
import projects.sanders87.nodes.messages.S87YesMessage;
import projects.sanders87.nodes.states.S87State;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;

public class S87Node extends Node {

	public S87State state;
	public int votes = 0;
	public boolean hasVoted = false;
	public boolean isInquired = false;
	
	@Override
	public void handleMessages(Inbox inbox) {
		while(inbox.hasNext()) {
			S87MessageHandler handler = new S87MessageHandler(this, (S87Node) inbox.getSender());
			S87Message message = (S87Message) inbox.next();

			if (message instanceof S87InquireMessage) {
				handler.handle((S87InquireMessage) message);
			} else if (message instanceof S87ReleaseMessage) {
				handler.handle((S87ReleaseMessage) message);
			} else if (message instanceof S87RelinquishMessage) {
				handler.handle((S87RelinquishMessage) message);
			} else if (message instanceof S87RequestMessage) {
				handler.handle((S87RequestMessage) message);
			} else if (message instanceof S87YesMessage) {
				handler.handle((S87YesMessage) message);
			}
		}
	}

	@Override
	public void preStep() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		state = S87State.NOT_IN_CS;
		
	}

	@Override
	public void postStep() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Graphics g, PositionTransformation pt, boolean highlight) {
		super.drawNodeAsDiskWithText(g, pt, highlight, this.toString(), 20, Color.WHITE);
	}
	
	@Override
	public Color getColor() {
		switch (state) {
		case NOT_IN_CS: return Color.RED;
		case WAITING: return Color.ORANGE;
		case IN_CS: return Color.GREEN;
		default: return Color.BLACK;
		}
	}
	
	public int getRow(int gridSize) {
		return (this.ID - 1) % gridSize;
	}
	
	public int getColumn(int gridSize) {
		return (this.ID - 1) / gridSize;
	}
		
	@Override
	public String toString() {
		return String.format("%03d", this.ID);
	}
	
	@Override
	public void checkRequirements() throws WrongConfigurationException {}
	
	@Override
	public void neighborhoodChange() {}
}
