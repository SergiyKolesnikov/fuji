package server;

import common.TextMessage;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {

	private String myName;

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage message = (TextMessage) msg;
			String content = message.getContent();
			if (content.startsWith("/msg")) {
				String[] split = content.split(" ", 3);

				message.setEmpfaenger(split[1]);
				message.setContent(split[2]);
			}

			original(name, message);

		}
	}

	public String getMyName() {
		return this.myName;

	}
}
