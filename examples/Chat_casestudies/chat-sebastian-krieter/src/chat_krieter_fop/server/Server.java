package server; 

import java.io.IOException; 
import java.net.ServerSocket; 
import java.net.Socket; 
import java.net.InetAddress; 
import java.util.HashMap; 
import java.util.Iterator; 

import server.Connection; 

import common.TextMessage; 

public   class  Server  extends Thread {
	
	
	private final ServerSocket serverSocket;

	
	
	protected HashMap<String, Connection> connections = new HashMap<String, Connection>();

	
	
	public static void main(String args[]) throws IOException {
		if (args.length != 1)
			throw new RuntimeException("Syntax: ChatServer <port>");
		
		new Server(Integer.parseInt(args[0]));
	}

	

	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 * @throws IOException 
	 */
	private Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		this.start();
	}

	

	@Override
	public void run() {
		try {
			System.out.println(InetAddress.getLocalHost());
			System.out.println(Integer.toString(serverSocket.getLocalPort()));
			printPassword();
			while (true) {
				Socket client = serverSocket.accept();
				System.out.println("Accepted from "+ client.getInetAddress().toString());
				new Connection(client, this);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * send a message to all known connections
	 * 
	 * @param text
	 *            content of the message
	 */	
	 private void  broadcast__wrappee__Spamfilter  (TextMessage msg) {
		synchronized (connections) {
			for (Iterator<Connection> it = connections.values().iterator(); it.hasNext();) {
				it.next().send(msg);
			}
		}
	}

	
	
	public void broadcast(TextMessage msg) {
		if (msg.getReceiver() == null) {
			broadcast__wrappee__Spamfilter(msg);
		} else {
			synchronized (connections) {
				Connection conR = connections.get(msg.getReceiver());
				Connection conS = connections.get(msg.getSender());
				if (conR != null) {
					conR.send(msg);
					if (conS != null) {
						conS.send(msg);
					}
				} else {
					if (conS != null) {
						conS.send(new TextMessage(TextMessage.SERVER_MESSAGE, "no such user"));
					}
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
	public void removeConnection(String username) {
		synchronized (connections) {
			connections.remove(username);
		}
	}

	
	
	public void addConnection(String username, Connection con) {
		synchronized (connections) {
			connections.put(username, con);
		}
	}

	
	
	private void printPassword  () {
		System.out.println("Password = " + PASSWORD);
	}

	
	
	public static final String PASSWORD = "123456";

	
	
	public void changeUsername(String oldName, String newName) {
		synchronized (connections) {
			Connection con = connections.remove(oldName);
			connections.put(newName, con);
		}
		broadcast(new TextMessage(TextMessage.SERVER_MESSAGE, oldName + " changed name to " + newName));
	}


}
