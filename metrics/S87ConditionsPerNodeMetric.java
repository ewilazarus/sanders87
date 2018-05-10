package projects.sanders87.metrics;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class S87ConditionsPerNodeMetric {
	public long waiting = 0;
	public long notInCS = 0;
	public long inCS = 0;
	
	public LocalDateTime lastWaitingTimestamp;
	public List<Long> msToCS = new ArrayList<>();
	
	public void isWaiting() {
		lastWaitingTimestamp = LocalDateTime.now();
	}
	
	public void isInCS() {
		if (lastWaitingTimestamp == null) return;
		
		msToCS.add(lastWaitingTimestamp.until(LocalDateTime.now(), ChronoUnit.MILLIS));
		lastWaitingTimestamp = null;
	}
}
