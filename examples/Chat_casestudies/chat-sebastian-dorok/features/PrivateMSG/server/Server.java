package server;

import java.util.HashSet;

import server.Connection;

import common.message.AbstractMessage;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {

	public boolean sendPrivateMSG(AbstractMessage msg, String to) {
		boolean found = false;
		synchronized (connections) {
			for (Connection conn : connections) {
				if (conn.getUserName().equals(to)) {
					try {
						conn.send((AbstractMessage) msg.clone());
						found = true;
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return found;
	}
}
