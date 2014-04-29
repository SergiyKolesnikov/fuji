package client;

import common.logging.ILogger;
import common.logging.LoggerFactory;

/**
 * simple chat client
 */
public class Client implements Runnable {

	// by default the logger is doing nothing
	private ILogger log = LoggerFactory.getEmptyLogger();

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(TextMessage msg) {
		this.log.log(msg.getFrom() + ":" + msg.getContent());
		original(msg);
	}

	public void fireStatusLine(StatusMessage msg) {
		this.log.log(msg.getStatus() + ":" + msg.getReason());
		original(msg);
	}

	public void connect(String user, String password) {
		original(user, password);
		this.log = LoggerFactory.getFileLogger(getUser() + ".log");
		log.log("Connected to " + host + ":" + port + " ...");
	}

}
