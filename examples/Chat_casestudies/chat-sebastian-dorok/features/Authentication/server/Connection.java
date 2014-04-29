package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import common.message.StatusMessage;
import common.message.AuthMessage;
import common.message.AbstractMessage;
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
		if (msg instanceof AuthMessage) {
			AuthMessage m = (AuthMessage) msg;
			if (this.server.grantAccess(m.getUser(), m.getPassword())) {
				this.username = m.getUser();
				send(new StatusMessage("Authentication successful. Welcome "
						+ this.username + "!", STATUS.AUTH_SUCC));
			} else {
				send(new StatusMessage("Unknown user or password.",
						STATUS.AUTH_FAIL));

				// original behavior mustn't be executed
				return;
			}
		}
		original(msg);
	}
}
