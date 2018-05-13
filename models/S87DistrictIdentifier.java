package projects.sanders87.models;

import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sinalgo.models.ConnectivityModelHelper;
import sinalgo.nodes.Node;

public class S87DistrictIdentifier extends ConnectivityModelHelper {
		
	private static class Districts
	{
		public static final Map<Integer, List<Integer>> nodeDistrictsMap = new HashMap<>();
		static {
			//*
			nodeDistrictsMap.put(1, asList(1, 4));
			nodeDistrictsMap.put(2, asList(2, 4));
			nodeDistrictsMap.put(3, asList(3, 4));
			nodeDistrictsMap.put(4, asList(1, 5));
			nodeDistrictsMap.put(5, asList(2, 5));
			nodeDistrictsMap.put(6, asList(3, 5));
			nodeDistrictsMap.put(7, asList(1, 6));
			nodeDistrictsMap.put(8, asList(2, 6));
			nodeDistrictsMap.put(9, asList(3, 6));
			/*/
			nodeDistrictsMap.put(1, asList(1, 6));
			nodeDistrictsMap.put(2, asList(2, 6));
			nodeDistrictsMap.put(3, asList(3, 6));
			nodeDistrictsMap.put(4, asList(4, 6));
			nodeDistrictsMap.put(5, asList(5, 6));
			nodeDistrictsMap.put(6, asList(1, 7));
			nodeDistrictsMap.put(7, asList(2, 7));
			nodeDistrictsMap.put(8, asList(3, 7));
			nodeDistrictsMap.put(9, asList(4, 7));
			nodeDistrictsMap.put(10, asList(5, 7));
			nodeDistrictsMap.put(11, asList(1, 8));
			nodeDistrictsMap.put(12, asList(2, 8));
			nodeDistrictsMap.put(13, asList(3, 8));
			nodeDistrictsMap.put(14, asList(4, 8));
			nodeDistrictsMap.put(15, asList(5, 8));
			nodeDistrictsMap.put(16, asList(1, 9));
			nodeDistrictsMap.put(17, asList(2, 9));
			nodeDistrictsMap.put(18, asList(3, 9));
			nodeDistrictsMap.put(19, asList(4, 9));
			nodeDistrictsMap.put(20, asList(5, 9));
			nodeDistrictsMap.put(21, asList(1, 10));
			nodeDistrictsMap.put(22, asList(2, 10));
			nodeDistrictsMap.put(23, asList(3, 10));
			nodeDistrictsMap.put(24, asList(4, 10));
			nodeDistrictsMap.put(25, asList(5, 10));
			//*/
		}
	}
	
	@Override
	protected boolean isConnected(Node from, Node to) {
		List<Integer> fromDistricts = Districts.nodeDistrictsMap.get(from.ID);
		List<Integer> toDistricts = Districts.nodeDistrictsMap.get(to.ID);
		return !Collections.disjoint(fromDistricts, toDistricts);
	}
	
}
