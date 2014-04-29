package server;

import java.util.Iterator;

import server.Connection;

/**
 * TODO description
 */
public class Server {
	public void sendToRealName(String realName, String text) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				if(connection.user != null && connection.user.realName.equalsIgnoreCase(realName))
					connection.send(text);
			}
		}
	}
}