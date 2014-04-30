
package NewEquation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;





/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
abstract class Server$$Chatbasic {

	public static void main(String[] args) throws IOException {
		if (args.length != 1)
			throw new RuntimeException("Syntax: ChatServer <port>");
		new Server(Integer.parseInt(args[0]));
	}

	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server$$Chatbasic(int port) throws IOException {
		ServerSocket server = new ServerSocket(port);
		while (true) {
			System.out.println("Waiting for Connections...");
			Socket client = server.accept();
			System.out.println("Accepted from " + client.getInetAddress());						
			connectioninit(client);					
		}
	}
	
	public void connectioninit(Socket client){
		Connection c=new Connection(client,((Server) this));
		c.start();
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
		Connection connection = new Connection(socket, ((Server) this));
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
	 * @param msg
	 *            object of the message
	 */
	public void broadcast(TextMessage msg) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
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

	public void serverlog(TextMessage msg){
	
	}
}



abstract class Server$$Historie extends  Server$$Chatbasic  {

	public void broadcast(TextMessage msg) {	
		serverlog(msg);
		super.broadcast(msg);
	}
	
	public void serverlog(TextMessage msg){
		
		msg.log("c:/Serverlog.dat");
	
	}
      // inherited constructors



	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server$$Historie ( int port )  throws IOException{ super(port); }

}



public class Server extends  Server$$Historie  {

	public void serverlog(TextMessage msg){
		
		msg.entverschieben();
		super.serverlog(msg);
		msg.verschieben();
	
	}
      // inherited constructors



	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server ( int port )  throws IOException{ super(port); }

}