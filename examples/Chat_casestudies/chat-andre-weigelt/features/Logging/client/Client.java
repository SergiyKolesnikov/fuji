package client;

import client.ChatLogger;
import common.TextMessage;

/**
 * simple chat client
 */
public class Client implements Runnable {
	ChatLogger logger = new ChatLogger();

	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			original(msg);
			logger.logMessage((TextMessage) msg);
		}
	}

}
