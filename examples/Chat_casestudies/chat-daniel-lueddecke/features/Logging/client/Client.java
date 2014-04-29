package client;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * simple chat client
 */
public class Client implements Runnable, IClientProxy {
	private static Logger logger = Logger.getLogger(Client.class); 
	private final static String canLogFile = "log/log.log";
	
	private static FileAppender getAppender(String fileName) {  
        RollingFileAppender appender = null;  
        try {  
            appender = new RollingFileAppender(  
                    new PatternLayout("%d{MM/dd/yyy - HH:mm:ss}: %m%n"), fileName, true);
            appender.setMaxBackupIndex(5);  
            appender.setMaxFileSize("1MB");  
        } catch (IOException ex) {  
            System.out.println("Could not create appender for " + fileName + ":" + ex.getMessage());  
        }  
        return appender;  
    }
	
	public static void main(String args[]) throws IOException {
		
		logger.setLevel(Level.ALL);
		
		if(canLogFile != null)
			logger.addAppender(getAppender(canLogFile));
		
		original(args);
	}

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(String line) {
		logger.info(line);
		
		original(line);
	}
}
