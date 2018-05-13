package projects.sanders87.reports;

import projects.sanders87.metrics.S87MetricCollector;
import sinalgo.tools.logging.Logging;

public class S87ReportDumper {
	
	private static Logging logger = Logging.getLogger("sanders87.log");
	private static Logging loggerCsv = Logging.getLogger("sanders87.csv");
		
	public static void dumpCsvHeaders() {
		StringBuilder sb = new StringBuilder();
		sb.append("round");
		sb.append(",");
		sb.append("total messages");
		sb.append(",");
		sb.append("avg messages p/ node");
		sb.append(",");
		sb.append("total unicasts");
		sb.append(",");
		sb.append("avg unicasts p/ node");
		sb.append(",");
		sb.append("total broadcasts");
		sb.append(",");
		sb.append("avg broadcasts p/ node");
		sb.append(",");
		sb.append("total inq messages");
		sb.append(",");
		sb.append("avg inq messages p/ node");
		sb.append(",");
		sb.append("total release messages");
		sb.append(",");
		sb.append("avg release messages p/ node");
		sb.append(",");
		sb.append("total relinquish messages");
		sb.append(",");
		sb.append("avg relinquish messages p/ node");
		sb.append(",");
		sb.append("total request messages");
		sb.append(",");
		sb.append("avg request messages p/ node");
		sb.append(",");
		sb.append("total yes messages");
		sb.append(",");
		sb.append("avg yes messages p/ node");
		sb.append(",");
		sb.append("number of times in CS");
		sb.append(",");
		sb.append("avg number of times in CS p/ node");
		sb.append(",");
		sb.append("number of times in NOT_IN_CS");
		sb.append(",");
		sb.append("avg number of times in NOT_IN_CS p/ node");
		sb.append(",");
		sb.append("number of times in WAITING");
		sb.append(",");
		sb.append("avg number of times in WAITING p/ node");
		sb.append(",");
		sb.append("avg milliseconds to transition from WAITING to IN_CS");
		
		loggerCsv.logln(sb.toString());
	}
	
	public static void dumpMetrics(int currentRound) {
		
		logger.logln("# STATISTICS UNITL ROUND #" + currentRound);
		logger.logln();
		logger.logln("* Communication");
		logger.logln();
		logger.logln("- Total messages: " + S87MetricCollector.totalNumberOfMessages());
		logger.logln("- Average number of messages per node: " + S87MetricCollector.avgNumberOfMessagesPerNode());
		logger.logln("- Total unicasts: " + S87MetricCollector.totalNumberOfUnicasts() + " (" + S87MetricCollector.relativeNumberOfUnicasts() + ")");
		logger.logln("- Average number of unicasts per node: " + S87MetricCollector.avgNumberOfUnicastsPerNode() + " (" + S87MetricCollector.relativeNumberOfUnicastsPerNode() + ")");
		logger.logln("- Total broadcasts: " + S87MetricCollector.totalNumberOfBroadcasts() + " (" + S87MetricCollector.relativeNumberOfBroadcasts() + ")");
		logger.logln("- Average number of broadcasts per node: " + S87MetricCollector.avgNumberOfBroadcastsPerNode() + " (" + S87MetricCollector.relativeNumberOfBroadcastsPerNode() + ")");
		logger.logln("- Total number of inquire messages: " + S87MetricCollector.totalNumberOfInquireMessages() + " (" + S87MetricCollector.relativeNumberOfInquireMessages() + ")");
		logger.logln("- Average number of inquire messages per node: " + S87MetricCollector.avgNumberOfInquireMessagesPerNode() + " (" + S87MetricCollector.relativeNumberOfInquireMessagesPerNode() + ")");
		logger.logln("- Total number of release messages: " + S87MetricCollector.totalNumberOfReleaseMessages() + " (" + S87MetricCollector.relativeNumberOfReleaseMessages() + ")");
		logger.logln("- Average number of release messages per node: " + S87MetricCollector.avgNumberOfReleaseMessagesPerNode() + " (" + S87MetricCollector.relativeNumberOfReleaseMessagesPerNode() + ")");
		logger.logln("- Total number of relinquish messages: " + S87MetricCollector.totalNumberOfRelinquishMessages() + " (" + S87MetricCollector.relativeNumberOfRelinquishMessages() + ")");
		logger.logln("- Average number of relinquish messages per node: " + S87MetricCollector.avgNumberOfRelinquishMessagesPerNode() + " (" + S87MetricCollector.relativeNumberOfRelinquishMessagesPerNode() + ")");
		logger.logln("- Total number of request messages: " + S87MetricCollector.totalNumberOfRequestMessages() + " (" + S87MetricCollector.relativeNumberOfRequestMessages() + ")");
		logger.logln("- Average number of request messages per node: " + S87MetricCollector.avgNumberOfRequestMessagesPerNode() + " (" + S87MetricCollector.relativeNumberOfRequestMessagesPerNode() + ")");
		logger.logln("- Total number of yes messages: " + S87MetricCollector.totalNumberOfYesMessages() + " (" + S87MetricCollector.relativeNumberOfYesMessages() + ")");
		logger.logln("- Average number of yes messages per node: " + S87MetricCollector.avgNumberOfYesMessagesPerNode() + " (" + S87MetricCollector.relativeNumberOfYesMessagesPerNode() + ")");
		logger.logln();
		logger.logln("* Conditions");
		logger.logln();
		logger.logln("- Number of times in CS: " + S87MetricCollector.totalNumberOfInCSNodes());
		logger.logln("- Average number of times in CS per node: " + S87MetricCollector.avgNumberOfInCSNodes());
		logger.logln("- Number of times in NOT_IN_CS: " + S87MetricCollector.totalNumberOfNotInCSNodes());
		logger.logln("- Average number of times in NOT_IN_CS per node: " + S87MetricCollector.avgNumberOfNotInCSNodes());
		logger.logln("- Number of times in WAITING: " + S87MetricCollector.totalNumberOfWaitingNodes());
		logger.logln("- Average number of times in WAITING per node: " + S87MetricCollector.avgNumberOfWaitingNodes());
		logger.logln("- Average milliseconds to transition from WAITING to IN_CS: " + S87MetricCollector.avgMillisecondsToCS());
		logger.logln();
		logger.logln();
						
		
		StringBuilder sb = new StringBuilder();
		sb.append(currentRound);
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfMessages());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfMessagesPerNode());
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfUnicasts());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfUnicastsPerNode());
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfBroadcasts());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfBroadcastsPerNode());
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfInquireMessages());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfInquireMessagesPerNode());
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfReleaseMessages());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfReleaseMessagesPerNode());
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfRelinquishMessages());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfRelinquishMessagesPerNode());
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfRequestMessages());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfRequestMessagesPerNode());
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfYesMessages());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfYesMessagesPerNode());
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfInCSNodes());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfInCSNodes());
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfNotInCSNodes());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfNotInCSNodes());
		sb.append(",");
		sb.append(S87MetricCollector.totalNumberOfWaitingNodes());
		sb.append(",");
		sb.append(S87MetricCollector.avgNumberOfWaitingNodes());
		sb.append(",");
		sb.append(S87MetricCollector.avgMillisecondsToCS());
		
		loggerCsv.logln(sb.toString());
	}
	
}
