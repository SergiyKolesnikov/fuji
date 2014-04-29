package common;

import org.apache.log4j.*;



public class Log {
	
	private static Logger logger = Logger.getLogger(Log.class);
	
	static { 
		init();
	}
	
	private static void init() { 
		try {
			FileAppender fileAppender = new RollingFileAppender(new TTCCLayout(), "./Historie.log", false);
			BasicConfigurator.configure(fileAppender);
		} catch (Exception e) {
			BasicConfigurator.configure();
		}
	}
	
	public static void info(String msg) { 
		logger.info(msg);
	}
}