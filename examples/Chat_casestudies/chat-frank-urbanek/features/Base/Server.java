
import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {
	ServerInfo info;
	private int port = 8081;

	public static void main(String args[]) throws IOException {
		if (args.length == 0)
			throw new RuntimeException("Syntax: ChatServer <port>");

		new Server(args);
	}

	private void readConfig(String[] args) {
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server(String[] args) throws IOException {
		readConfig(args);
		init();
		info = new ServerInfo("Server");
		ServerSocket server = new ServerSocket(port);
		while (true) {
			System.out.println("Waiting for Connections...");
			Socket client = server.accept();
			System.out.println("Accepted from " + client.getInetAddress());
			Connection c = connectTo(client);
			c.start();
		}
	}

	private void init() {
		/*
		cipher = new CipherNone();
		if (Config.CIPHER == Cipher.Codec.ROT13) {
			cipher = new CipherROT13();
		}
		if (Config.CIPHER == Cipher.Codec.SWITCH) {
			cipher = new CipherSwitch();
		}*/
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

	public void broadcast(Message msg) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				connection.send(msg);
			}
		}
	}

	public Message createServerMessage(String text) {
		MessageContext context = new MessageContext(info);
		TextMessage message = new TextMessage(cipher.encode(text));
		// #if Color
//@		 message.setColor(Color.RED);
		// #endif
		ServerMessage msg = new ServerMessage(context, message);
		return msg;
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
