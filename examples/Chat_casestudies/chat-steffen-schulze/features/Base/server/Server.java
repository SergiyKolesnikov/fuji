package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import common.User;
import common.Users;

import server.Connection;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {
	
	public final static String NAME = "server";
	public List<User> users;

	public static void main(String args[]) throws IOException {
		if (args.length == 0)
			throw new RuntimeException("Syntax: ChatServer <port>");

		new Server(Integer.parseInt(args[0]));
	}
	
	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server(int port) throws IOException {
		ServerSocket server = new ServerSocket(port);
		users = new ArrayList<User>();
		users.add(Users.getUser(NAME));
		
		while (true) {
			System.out.println("Waiting for Connections...");
			Socket client = server.accept();
			System.out.println("Accepted from " + client.getInetAddress());
			Connection c = connectTo(client);
			c.start();
		}
	}

	/**
	 * creates a new connection for a socket
	 * 
	 * @param socket
	 *            socket
	 * @return the Connection object that handles all further communication with
	 *         this socket
	 */
	public Connection connectTo(Socket socket) {
		Connection connection = new Connection(socket, this);
		connections.add(connection);
		return connection;
	}

	/**
	 * list of all known connections
	 */
	protected HashSet<Connection> connections = new HashSet<Connection>();

	/**
	 * send a message to all known connections
	 * 
	 * @param text
	 *            content of the message
	 */
	public void broadcast(String sender, String text) {
		synchronized (connections) {
			for (Iterator<Connection> iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(sender, text);
			}
		}
	}

	/**
	 * remove a connection so that broadcasts are no longer sent there.
	 * 
	 * @param connection
	 *            connection to remove
	 */
	public void removeConnection(Connection connection) {
		connections.remove(connection);
	}

	public boolean checkIfUsernameExist(String username)
	{
		for (User user : users) {
			if (user.getUsername().equals(username)) return true;
		}
		
		return false;
	}
}
