package projects.sanders87.nodes.timers;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.nodes.timers.Timer;

public class S87Timer extends Timer {

	@Override
	public void fire() {
		((S87Node) this.node).leaveCS();
	}
	
}
