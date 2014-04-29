package server;

import java.net.Socket;
import java.util.HashMap;

import server.Connection;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {

	private HashMap<String, Connection> connectionByName = new HashMap<String, Connection>();

	/**
	 * creates a new connection for a socket
	 * 
	 * @param socket
	 *            socket
	 * @return the Connection object that handles all further communication with
	 *         this socket
	 */
	public Connection connectTo(Socket socket) {
		Connection connection = original(socket);
		connectionByName.put(connection.getClientName(), connection);
		return connection;
	}

	/**
	 * remove a connection so that broadcasts are no longer sent there.
	 * 
	 * @param connection
	 *            connection to remove
	 */
	public void removeConnection(Connection connection) {
		original(connection);
		connectionByName.remove(connection.getClientName()); 
	}
	
	public boolean updateClientName(Connection connection, String name) {
		if(connectionByName.containsKey(name))
			return false;
		
		connectionByName.remove(connection.getClientName());
		connectionByName.put(name, connection);
		return true;		
	}
	
	public boolean sendPrivateMessage(String username, TextMessage msg) {
		Connection connection = connectionByName.get(username);
		
		if(connection == null)
			return false;
		
		connection.send(msg);		
		return true;
	}

}
