package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;


import common.TextMessage;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {
	private static final String PASSWORD = "password";

	/**
	 * list of all known connections
	 */
	@SuppressWarnings("rawtypes")
	protected HashSet connections = new HashSet();

	/**
	 * send a message to all known connections
	 * 
	 * @param text
	 *            content of the message
	 */
	public void broadcast(String text) {
		broadcast(new TextMessage(text));
	}

	public void broadcast(TextMessage msg) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(msg);
			}
		}
	}


	@SuppressWarnings({ "unchecked" })
	public void loginClient(Connection con) {
		broadcast(con.socket.getInetAddress().toString() + " has joined.");
		connections.add(con);
	}

	public boolean authenticate(TextMessage msg) {
		if (msg != null && msg.getContent().equals(PASSWORD)) {
			return true;
		} else {
			return false;
		}
	}
}
