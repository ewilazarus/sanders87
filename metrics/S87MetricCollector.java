package projects.sanders87.metrics;

import java.util.HashMap;
import java.util.Map;

import projects.sanders87.models.nodes.S87AbstractNode;
import projects.sanders87.models.messages.S87AbstractMessage;

public class S87MetricCollector {
	
	public static void countMessage(S87AbstractMessage message) {
		doCountMessages(message, 1, false);
	}
	
	public static void countMessages(S87AbstractMessage message, long number) {
		doCountMessages(message, number, true);
	}
	
	public static long numberOfCommunicatingNodes() {
		return messageCounter.size();
	}
	
	public static long totalNumberOfMessages() {
		return totalM((metric) -> metric.total);
	}
	
	public static double avgNumberOfMessagesPerNode() {
		return avgM((metric) -> metric.total);
	}
			
	public static long totalNumberOfUnicasts() {
		return totalM((metric) -> metric.unicasts);
	}
	
	public static double avgNumberOfUnicastsPerNode() {
		return avgM((metric) -> metric.unicasts);
	}
	
	public static long totalNumberOfBroadcasts() {
		return totalM((metric) -> metric.broadcasts);
	}
	
	public static double avgNumberOfBroadcasts() {
		return avgM((metric) -> metric.broadcasts);
	}
		
	public static long totalNumberOfInquireMessages() {
		return totalM((metric) -> metric.inquire);
	}
	
	public static double avgNumberOfInquireMessagesPerNode() {
		return avgM((metric) -> metric.inquire);
	}
	
	public static long totalNumberOfReleaseMessages() {
		return totalM((metric) -> metric.release);
	}
	
	public static double avgNumberOfReleaseMessagesPerNode() {
		return avgM((metric) -> metric.release);
	}
	
	public static long totalNumberOfRelinquishMessages() {
		return totalM((metric) -> metric.relinquish);
	}
	
	public static double avgNumberOfRelinquishMessagesPerNode() {
		return avgM((metric) -> metric.relinquish);
	}
	
	public static long totalNumberOfRequestMessages() {
		return totalM((metric) -> metric.request);
	}
	
	public static double avgNumberOfRequestMessagesPerNode() {
		return avgM((metric) -> metric.request);
	}
	
	public static long totalNumberOfYesMessages() {
		return totalM((metric) -> metric.yes);
	}
	
	public static double avgNumberOfYesMessagesPerNode() {
		return avgM((metric) -> metric.yes);
	}
	
	public static void countCondition(S87AbstractNode node) {
		S87ConditionsPerNodeMetric cpn = new S87ConditionsPerNodeMetric();
		if (conditionsCounter.containsKey(node.label)) {
			cpn = conditionsCounter.get(node.label);
		}
		
		switch (node.getCondition()) {
		case WAITING:
			cpn.isWaiting();
			cpn.waiting++; 
			break;
					
		case IN_CS:
			cpn.isInCS();
			cpn.inCS++; 
			break;
					
		case NOT_IN_CS: cpn.notInCS++; break;
		}
		
		conditionsCounter.put(node.label, cpn);
	}
		
	public static long numberOfConditionTransientNodes() {
		return conditionsCounter.size();
	}
	
	public static long totalNumberOfInCSNodes() {
		return totalC((metric) -> metric.inCS);
	}
	
	public static double avgNumberOfInCSNodes() {
		return avgC((metric) -> metric.inCS);
	}
	
	public static long totalNumberOfNotInCSNodes() {
		return totalC((metric) -> metric.notInCS);
	}
	
	public static double avgNumberOfNotInCSNodes() {
		return avgC((metric) -> metric.notInCS);
	}
		
	public static long totalNumberOfWaitingNodes() {
		return totalC((metric) -> metric.waiting);
	}
	
	public static double avgNumberOfWaitingNodes() {
		return avgC((metric) -> metric.waiting);
	}
	
	public static double avgMillisecondsToCS() {
		double avgTimeToCS = 0;
		for (Map.Entry<String, S87ConditionsPerNodeMetric> entry : conditionsCounter.entrySet()) {
			S87ConditionsPerNodeMetric metric = entry.getValue();
			if (!metric.msToCS.isEmpty()) {
				long sumMs = 0;
				for (long ms : metric.msToCS) sumMs += ms;
				avgTimeToCS += ((double) sumMs) / metric.msToCS.size();	
			}
		}
		return avgTimeToCS / conditionsCounter.size();
	}
			
	private static final Map<String, S87MessagesPerNodeMetric> messageCounter = new HashMap<>();
	private static final Map<String, S87ConditionsPerNodeMetric> conditionsCounter = new HashMap<>();
			
	private interface MPredicate {
		long extract(S87MessagesPerNodeMetric metric);
	}
	
	private interface CPredicate {
		long extract(S87ConditionsPerNodeMetric metric);
	}
	
	private static void doCountMessages(S87AbstractMessage message, long number, boolean isBroadcast) {
		S87MessagesPerNodeMetric mpn = new S87MessagesPerNodeMetric();
		if (messageCounter.containsKey(message.sender.label)) {
			mpn = messageCounter.get(message.sender.label);
		}
		
		if (isBroadcast) {
			mpn.broadcasts++;
		} else {
			mpn.unicasts++;
		}
		
		switch (message.type) {
		case "Inquire": mpn.inquire += number; break;
		case "Release": mpn.release += number; break;
		case "Relinquish": mpn.relinquish += number; break;
		case "Request": mpn.request += number; break;
		case "Yes": mpn.yes += number; break;
		}
		
		mpn.total += number;
		
		messageCounter.put(message.sender.label, mpn);
	}
	
	private static long totalM(MPredicate predicate) {
		long messageCount = 0;
		for (Map.Entry<String, S87MessagesPerNodeMetric> entry : messageCounter.entrySet()) {
			S87MessagesPerNodeMetric metric = entry.getValue();
			messageCount += predicate.extract(metric);
		}
		return messageCount;
	}
		
	private static double avgM(MPredicate predicate) {
		long numberOfNodes = numberOfCommunicatingNodes();
		long messageCount = totalM(predicate);
		
		return ((double) messageCount) / numberOfNodes;
	}
	
	private static long totalC(CPredicate predicate) {
		long conditionCount = 0;
		for (Map.Entry<String, S87ConditionsPerNodeMetric> entry : conditionsCounter.entrySet()) {
			S87ConditionsPerNodeMetric metric = entry.getValue();
			conditionCount += predicate.extract(metric);
		}
		return conditionCount;
	}
	
	private static double avgC(CPredicate predicate) {
		long numberOfNodes = numberOfCommunicatingNodes();
		long conditionCount = totalC(predicate);
		
		return ((double) conditionCount) / numberOfNodes;
	}
}
