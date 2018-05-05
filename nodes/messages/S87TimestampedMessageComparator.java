package projects.sanders87.nodes.messages;

import java.util.Comparator;

public class S87TimestampedMessageComparator implements Comparator<S87TimestampedMessage> {

	@Override
	public int compare(S87TimestampedMessage m1, S87TimestampedMessage m2) {
		if (m1.timestamp < m2.timestamp) {
			return -1;
		} else if (m1.timestamp > m2.timestamp) {
			return 1;
		} else if (m1.sender.ID < m2.sender.ID) {
			return -1;
		} else if (m1.sender.ID > m2.sender.ID) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
