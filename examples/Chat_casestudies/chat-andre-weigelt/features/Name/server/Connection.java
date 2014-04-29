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
			String content = ((TextMessage) msg).getContent();
			if (content.startsWith("/username")) {
				this.myName = content.substring(content.indexOf(" ") + 1);
				this.send("Name in " + this.myName + " geändert");
			} else {
				if (this.myName != null) {
					original(this.myName, msg);
				} else {
					original(name, msg);
				}
			}
		}
	}
	public void send(String line) {
		original(line);
	}
}
