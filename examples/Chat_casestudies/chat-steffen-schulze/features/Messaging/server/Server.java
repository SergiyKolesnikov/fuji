package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

import server.Connection;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {
	
	public void broadcast(String sender, String receiver, String text) {
		synchronized (connections) {
			for (Iterator<Connection> iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				if ((receiver == null) || (connection.getUsername().equals(receiver)))
				{
					connection.send(sender, text);
				}
			}
		}
	}
}
