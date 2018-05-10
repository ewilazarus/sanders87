package projects.sanders87.reports;

import projects.sanders87.metrics.S87MetricCollector;
import sinalgo.tools.logging.Logging;

public class S87ReportDumper {
	
	private static Logging logger = Logging.getLogger("sanders87.log");
		
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
						
	}
	
}
