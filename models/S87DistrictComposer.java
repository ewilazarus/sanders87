package projects.sanders87.models;

import sinalgo.models.ConnectivityModelHelper;
import sinalgo.nodes.Node;
import sinalgo.runtime.Runtime;

public class S87DistrictComposer extends ConnectivityModelHelper {
		
	@Override
	protected boolean isConnected(Node from, Node to) {
		int size = (int) Math.sqrt(Runtime.nodes.size());
		
		return isInTheSameRow(size, from, to) || isInTheSameColumn(size, from, to);
	}
	
	private boolean isInTheSameRow(int size, Node from, Node to) {
		int fromRow = (from.ID - 1) % size;
		int toRow = (to.ID - 1) % size;
		return fromRow == toRow;
	}
	
	private boolean isInTheSameColumn(int size, Node from, Node to) {
		int fromColumn = (from.ID - 1) / size;
		int toColumn = (to.ID - 1) / size;
		return fromColumn == toColumn;
	}
}
