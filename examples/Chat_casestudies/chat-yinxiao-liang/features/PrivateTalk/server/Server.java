package server;

import java.util.Iterator;

import server.Connection;

/**
 * TODO description
 */
public class Server {
	public void privatemessage(String text, String touser,String user) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				if(connection.username.equals(touser) || connection.username.equals(user))
				{
				connection.send(user + " to " + touser +" <whisper>- " + text);
				}
			}
		}
	}
	

}