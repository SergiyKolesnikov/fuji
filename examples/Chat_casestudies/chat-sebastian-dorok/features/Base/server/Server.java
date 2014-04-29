package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import common.message.AbstractMessage;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {

	public static void main(String args[]) {
		if (args.length < 1)
			throw new RuntimeException("Syntax: ChatServer <port>");

		new Server(Integer.parseInt(args[0]));
	}

	private int port;

	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server(int port) {
		this.port = port;
		init();
	}

	private void init() {
		try {
			ServerSocket server = new ServerSocket(this.port);
			System.out.println("Waiting for Connections...");
			while (true) {
				Socket client = server.accept();
				System.out.println("Accepted from " + client.getInetAddress());
				Connection c = connectTo(client);
				c.start();
			}
		} catch (IOException e) {

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
	public void broadcast(AbstractMessage msg) {
		synchronized (connections) {
			for (Connection conn : connections) {
				try {
					conn.send((AbstractMessage) msg.clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
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
