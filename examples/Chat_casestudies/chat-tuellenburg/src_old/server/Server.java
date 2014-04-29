package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import common.EncryptedMessage;
import common.Encrypter;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {
	
	//added 13.05: fixed password
	private final String password = "geheim";
	private File history;
	private BufferedWriter writer;
	private static Encrypter enc;

	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatServer <port> [rot13/scramble2]");
		
		//which encryption algorithm?
		enc = null;
		if (args[1].equals("rot13") || args[1].equals("scramble2")) {
			enc = new Encrypter(args[1]);
		}

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
		new File("ServerLogs" + System.getProperty("file.separator")).mkdir();
		history = new File("ServerLogs" + System.getProperty("file.separator") 
					      + "server_history" + (System.currentTimeMillis()/1000));
		writer = new BufferedWriter(new FileWriter(history));
		writer.write("Chat-Session: " + Calendar.getInstance().getTime());
		writer.newLine();
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
		Connection connection = new Connection(socket, this, enc);
		connections.add(connection);
		return connection;
	}

	/**
	 * list of all known connections
	 */
	protected HashSet connections = new HashSet();
	
	/**
	 * list auf all authenticated connections
	 * added 14.05
	 */
	protected HashSet authenticatedConnections = new HashSet();

	/**
	 * send a message to all authenticated connections
	 * 
	 * @param text
	 *            content of the message
	 */
	public void broadcast(String text) {
		synchronized (authenticatedConnections) {
			for (Iterator iterator = authenticatedConnections.iterator(); iterator.hasNext();) {
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
	public void removeConnection(Connection connection) {
		connections.remove(connection);
	}
	
	/**
	 * checkLogin: checks whether auth. Messages consists of the right
	 * password
	 */
	public void checkLogin(Connection connection, String password) {
		if (this.password.equals(password) != true) {
			
			connection.send("connection refused: wrong password");
			removeConnection(connection);
		} else {
			connection.send("connection accepted");
			authenticatedConnections.add(connection);
			connections.remove(connection);
		}
	}
	
	//adds entry to servers history
	//added 14.05
	
	public void addToHistory(String line){
		try {
			writer.write(line);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
