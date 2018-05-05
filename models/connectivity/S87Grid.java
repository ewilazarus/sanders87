package projects.sanders87.models.connectivity;

import projects.sanders87.nodes.implementations.S87Node;
import sinalgo.models.ConnectivityModelHelper;
import sinalgo.nodes.Node;
import sinalgo.runtime.Runtime;

public class S87Grid extends ConnectivityModelHelper {
	public int size;
	
	public S87Grid() {
		size = (int) Math.sqrt(Runtime.nodes.size());
	}
	
	@Override
	protected boolean isConnected(Node from, Node to) {
		return isInTheSameRow((S87Node) from, (S87Node) to) ||
			   isInTheSameColumn((S87Node) from, (S87Node) to);
	}
	
	private boolean isInTheSameRow(S87Node from, S87Node to) {
		return from.getRow(size) == to.getRow(size);
	}
	
	private boolean isInTheSameColumn(S87Node from, S87Node to) {
		return from.getColumn(size) == to.getColumn(size);
	}
		
}
