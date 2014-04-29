package server;

import common.message.StatusMessage.STATUS;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			String strMsg = ((TextMessage) msg).getContent();
			if (server.doesMessageContainSpam(strMsg)) {
				// spam, notify client
				send(new StatusMessage("Your previous message contains spam. "
						+ "Your message wasn't broadcasted.",
						STATUS.SERVER_WARN));
				// no broadcasting allowed
				return;
			}
		}

		original(msg);
	}

}
