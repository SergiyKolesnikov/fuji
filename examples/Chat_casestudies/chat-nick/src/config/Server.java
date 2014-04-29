
import java.io.IOException; 
import java.net.ServerSocket; 
import java.net.Socket; 
import java.util.HashSet; 
import java.util.Iterator; 

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public  class  Server {
	

	public static void main(String args[]) throws IOException {
		System.out.println(args.toString());
		if (args.length != 1)
			throw new RuntimeException("Syntax chat2: ChatServer <port>");
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
		while (true) {
			System.out.println("FOP Chat v2: Waiting for Connections...");
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
	protected HashSet connections = new HashSet();

	

	/**
	 * send a message to all known connections
	 * 
	 * @param text
	 *            content of the message
	 */
	public void broadcast(String host, String text) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				//text = host + " - " + text;
				connection.send(text);
			}
		}
	}

	
	
	public void broadcast(String host, TextMessage msg) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				//msg.setContent(host + " - " + msg.getContent()); 
				connection.send(msg);
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


}
