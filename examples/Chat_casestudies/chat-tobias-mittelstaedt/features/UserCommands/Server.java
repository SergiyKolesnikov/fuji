package server;

import java.util.Iterator;

import server.Connection;

public class Server {

	public void broadcast(String text) {

		String recipient = null;
		String[] temp = new String[3];
		
		if (text.startsWith("\recipient")) {
			temp = text.split(" ");

			synchronized (connections) {
				for (Iterator iterator = connections.iterator(); iterator
						.hasNext();) {
					Connection connection = (Connection) iterator.next();

					if (connection.getUsername() == temp[1])
						connection.send(temp[2]);
					return;
				}

			}
		}

		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(text);

			}
		}
	}

}