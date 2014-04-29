package common.logging;

import java.util.HashMap;

public class LoggerFactory {

	private static HashMap<String, FileLogger> loggerMap = new HashMap<String, FileLogger>();
	
	/**
	 * Retrieves logger for the specified filename.
	 * 
	 * @return logger singleton
	 */
	public static ILogger getFileLogger(String logFilename) {
		if (!LoggerFactory.loggerMap.containsKey(logFilename)) {
			LoggerFactory.loggerMap.put(logFilename, new FileLogger(logFilename));
		}
		return LoggerFactory.loggerMap.get(logFilename);
	}

	public static ILogger getEmptyLogger() {		
		return EmptyLogger.getInstance();
	}
	
}
