import java.util.Iterator;
import java.util.Vector;

import server.Connection;
import server.Server;

import common.UserMessage;

/**
 * TODO description
 */
public class Server {
	public static String password = "1";
	public static Vector userOnline = new Vector();

	public void removeConnection(Connection connection) {
	original(connection);
	userOnline.removeElement(connection.username);
	UserMessage um = new UserMessage();
	um.add = false;
	um.user= connection.username;
	broadcast( um );
	}
	public void broadcast(UserMessage um) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(um);
			}
		}
	}

}