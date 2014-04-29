package server;

import common.message.StatusMessage;
import common.message.StatusMessage.STATUS;
import common.message.TextMessage;

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
			// is it a private message
			if (strMsg.contains("/username")) {
				// split information to identify message and to part
				String[] arr = strMsg.split(" ");
				if (arr.length < 2) {
					System.out.println("Too few arguments for change username "
							+ "message format. "
							+ "Format: /username <username with "
							+ "no whitespaces>");
					return;
				}
				if (arr.length > 2) {
					System.out
							.println("Too many arguments for change username "
									+ "message format."
									+ "Format: /username <username with "
									+ "no whitespaces>");
					return;
				}
				String newuser = arr[1];
				server.broadcast(new StatusMessage(this.username
						+ " changed name to " + newuser, STATUS.SERVER_INFO));
				this.username = newuser;
				// skip further message processing
				return;
			}
		}

		original(msg);
	}
}
