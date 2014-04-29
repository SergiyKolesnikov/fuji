package server; 

import java.io.IOException; 
import java.net.ServerSocket; 
import java.net.Socket; 
import java.util.HashSet; 

import java.util.Iterator; 
import java.util.Vector; 

import server.Connection; 
import server.Server; 

import common.UserMessage; 

/**
 * TODO description
 */
public   class  Server {
	
	public static void main(String args[]) throws IOException, NumberFormatException, ClassNotFoundException {
		if (args.length != 1)
			throw new RuntimeException("Syntax: ChatServer <port>");
		new Server(Integer.parseInt(args[0]));
		System.out.println(Integer.parseInt(args[0]));
	}

	

	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 * @throws ClassNotFoundException 
	 */
	public Server(int port) throws IOException, ClassNotFoundException {
		ServerSocket server = new ServerSocket(port);
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
	 * @throws ClassNotFoundException 
	 */
	public Connection connectTo(Socket socket) throws ClassNotFoundException {
		Connection connection = new Connection(socket, this);
		connections.add(connection);
		return connection;
	}

	

	/**
	 * list of all known connections
	 */
	protected HashSet connections = new HashSet();

	

	/**
	 * send a message to all known connections
	 * 
	 * @param text
	 *            content of the message
	 */
	public void broadcast(String text) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(text);
			}
		}
	}

	

	/**
	 * remove a connection so that broadcasts are no longer sent there.
	 * 
	 * @param connection
	 *            connection to remove
	 */
	 private void  removeConnection__wrappee__Log  (Connection connection) {
		connections.remove(connection);
		
	}

	

	public void removeConnection(Connection connection) {
	removeConnection__wrappee__Log(connection);
	userOnline.removeElement(connection.username);
	UserMessage um = new UserMessage();
	um.add = false;
	um.user= connection.username;
	broadcast( um );
	}

	
	public static String password = "1";

	
	public static Vector userOnline = new Vector();

	
	public void broadcast(UserMessage um) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(um);
			}
		}
	}

	
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
