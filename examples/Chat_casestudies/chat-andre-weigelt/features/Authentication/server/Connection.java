package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import common.TextMessage;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	private boolean isAuthenticated = false;
	private Server server;

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
			if (isAuthenticated) {
				((TextMessage) msg).setSender(name);

				server.broadcast(((TextMessage) msg));
			} else {
				registerConnection((TextMessage) msg);
			}
		}
	}

	/**
	 * as long as this connection is not authenticated, all input will test as
	 * password
	 * 
	 * @param msg
	 */
	private void registerConnection(TextMessage msg) {
		if (!isAuthenticated) {
			this.setIsAuthenticated(this.server.authenticate(msg));
		}
		
		if (!isAuthenticated) {
			send("Bitte geben Sie ein Passwort ein zum einloggen:");
		} else {
			send("Sie sind eingeloggt!");
			server.loginClient(this);
		}
	}

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String line) {
		send(new TextMessage(line));
	}

	public void send(TextMessage msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
		}
	}
	
	public void setIsAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
}
