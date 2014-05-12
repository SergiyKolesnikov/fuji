

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;




/**
 * 
 * server's main class. accepts incoming connections and allows broadcasting
 * 
 */
abstract class Server$$Root{

	public static void main(String args[]) throws IOException {
		if (args.length != 1)
			throw new RuntimeException("Syntax: ChatServer <port>");
		new Server(Integer.parseInt(args[0]));
	}
	
	/**
	 * 
	 * list of all known connections
	 */
	protected HashSet connections = new HashSet();


	/**
	 * 
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server$$Root(int port) throws IOException {	
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
	 * 
	 * creates a new connection for a socket
	 * 
	 * @param socket
	 *            socket
	 * @return the Connection object that handles all further communication with
	 *         this socket
	 */
	public Connection connectTo(Socket socket) {
		Connection connection = new Connection(socket, ((Server) this));
		connections.add(connection);
		return connection;
	}

	/**
	 * 
	 * send a message to all known connections
	 * 
	 * @param text
	 *            content of the message
	 */
	public void broadcast(Message msg) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(msg);
			}
		}
	}
	
	/**
	 * 
	 * remove a connection so that broadcasts are no longer sent there.
	 * 
	 * @param connection
	 *            connection to remove
	 */
	public void removeConnection(Connection connection) {
		connections.remove(connection);
	}
}



public class Server extends  Server$$Root  {
	Logger log;
	
	public Logger getLogger(){
		if(log ==  null){
			log = new Logger("Server");
		}
		return log;
	}
      // inherited constructors




	/**
	 * 
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server ( int port )  throws IOException{ super(port); }	
}