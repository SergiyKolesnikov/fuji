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
			if (strMsg.contains("/msg")) {
				// split information to identify message and to part
				String[] arr = strMsg.split(" ");
				if (arr.length < 3) {
					server.sendPrivateMSG(new StatusMessage(
							"Wrong private message format."
									+ "Format: /msg <username with no "
									+ "whitespaces> <message>",
							STATUS.SERVER_WARN), this.username);
					return;
				}
				String to = arr[1];
				String content = "";
				for (int i = 2; i < arr.length; i++) {
					content += arr[i];
					if (i < arr.length - 1) {
						content += " ";
					}
				}
				// TODO color
				String from = this.username;
				TextMessage textMsg = new TextMessage(content);
				textMsg.setFrom(from);
				// send message to private receiver
				boolean delivered = server.sendPrivateMSG(textMsg, to);
				if (delivered) {
					// send to your self if private receiver found
					server.sendPrivateMSG(textMsg, from);
				} else {
					// delivery failed
					server.sendPrivateMSG(new StatusMessage(
							"Could not identify private receiver " + to,
							STATUS.SERVER_WARN), from);
				}
				// skip further message processing
				return;
			}
		}

		original(msg);
	}
}
