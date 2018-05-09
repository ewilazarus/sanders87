package projects.sanders87.metrics;

import java.util.HashMap;
import java.util.Map;

import projects.sanders87.nodes.implementations.S87AbstractNode;
import projects.sanders87.nodes.messages.S87Message;

public class S87MetricCollector {
	
	public static void countMessage(S87Message message) {
		doCountMessages(message, 1, false);
	}
	
	public static void countMessages(S87Message message, int number) {
		doCountMessages(message, number, true);
	}
	
	public static int numberOfCommunicatingNodes() {
		return messageCounter.size();
	}
	
	public static int totalNumberOfMessages() {
		return totalM((metric) -> metric.total);
	}
	
	public static float avgNumberOfMessagesPerNode() {
		return avgM((metric) -> metric.total);
	}
			
	public static int totalNumberOfUnicasts() {
		return totalM((metric) -> metric.unicasts);
	}
	
	public static float avgNumberOfUnicastsPerNode() {
		return avgM((metric) -> metric.unicasts);
	}
	
	public static int totalNumberOfBroadcasts() {
		return totalM((metric) -> metric.broadcasts);
	}
	
	public static float avgNumberOfBroadcasts() {
		return avgM((metric) -> metric.broadcasts);
	}
		
	public static int totalNumberOfInquireMessages() {
		return totalM((metric) -> metric.inquire);
	}
	
	public static float avgNumberOfInquireMessagesPerNode() {
		return avgM((metric) -> metric.inquire);
	}
	
	public static int totalNumberOfReleaseMessages() {
		return totalM((metric) -> metric.release);
	}
	
	public static float avgNumberOfReleaseMessagesPerNode() {
		return avgM((metric) -> metric.release);
	}
	
	public static int totalNumberOfRelinquishMessages() {
		return totalM((metric) -> metric.relinquish);
	}
	
	public static float avgNumberOfRelinquishMessagesPerNode() {
		return avgM((metric) -> metric.relinquish);
	}
	
	public static int totalNumberOfRequestMessages() {
		return totalM((metric) -> metric.request);
	}
	
	public static float avgNumberOfRequestMessagesPerNode() {
		return avgM((metric) -> metric.request);
	}
	
	public static int totalNumberOfYesMessages() {
		return totalM((metric) -> metric.yes);
	}
	
	public static float avgNumberOfYesMessagesPerNode() {
		return avgM((metric) -> metric.yes);
	}
	
	public static void countCondition(S87AbstractNode node) {
		S87ConditionsPerNodeMetric cpn = new S87ConditionsPerNodeMetric();
		if (conditionsCounter.containsKey(node.label)) {
			cpn = conditionsCounter.get(node.label);
		}
		
		switch (node.getCondition()) {
		case IN_CS: cpn.inCS++; break;
		case NOT_IN_CS: cpn.notInCS++; break;
		case WAITING: cpn.waiting++; break;
		}
		
		conditionsCounter.put(node.label, cpn);
	}
		
	public static int numberOfConditionTransientNodes() {
		return conditionsCounter.size();
	}
	
	public static int totalNumberOfInCSNodes() {
		return totalC((metric) -> metric.inCS);
	}
	
	public static float avgNumberOfInCSNodes() {
		return avgC((metric) -> metric.inCS);
	}
	
	public static int totalNumberOfNotInCSNodes() {
		return totalC((metric) -> metric.notInCS);
	}
	
	public static float avgNumberOfNotInCSNodes() {
		return avgC((metric) -> metric.notInCS);
	}
		
	public static int totalNumberOfWaitingNodes() {
		return totalC((metric) -> metric.waiting);
	}
	
	public static float avgNumberOfWaitingNodes() {
		return avgC((metric) -> metric.waiting);
	}
			
	private static Map<String, S87MessagesPerNodeMetric> messageCounter = new HashMap<>();
	private static Map<String, S87ConditionsPerNodeMetric> conditionsCounter = new HashMap<>();
	
	private interface MPredicate {
		int extract(S87MessagesPerNodeMetric metric);
	}
	
	private interface CPredicate {
		int extract(S87ConditionsPerNodeMetric metric);
	}
	
	private static void doCountMessages(S87Message message, int number, boolean isBroadcast) {
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
	
	private static int totalM(MPredicate predicate) {
		int messageCount = 0;
		for (Map.Entry<String, S87MessagesPerNodeMetric> entry : messageCounter.entrySet()) {
			S87MessagesPerNodeMetric metric = entry.getValue();
			messageCount += predicate.extract(metric);
		}
		return messageCount;
	}
		
	private static float avgM(MPredicate predicate) {
		int numberOfNodes = numberOfCommunicatingNodes();
		int messageCount = totalM(predicate);
		
		return messageCount / numberOfNodes;
	}
	
	private static int totalC(CPredicate predicate) {
		int conditionCount = 0;
		for (Map.Entry<String, S87ConditionsPerNodeMetric> entry : conditionsCounter.entrySet()) {
			S87ConditionsPerNodeMetric metric = entry.getValue();
			conditionCount += predicate.extract(metric);
		}
		return conditionCount;
	}
	
	private static float avgC(CPredicate predicate) {
		int numberOfNodes = numberOfCommunicatingNodes();
		int conditionCount = totalC(predicate);
		
		return conditionCount / numberOfNodes;
	}
}
