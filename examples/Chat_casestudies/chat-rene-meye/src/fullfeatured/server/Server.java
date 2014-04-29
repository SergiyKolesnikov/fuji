package server; 

import java.io.IOException; 
import java.net.ServerSocket; 
import java.net.Socket; 
import java.util.ArrayList; 
import java.util.HashSet; 

import java.util.Iterator; 
import java.util.Vector; 

import common.*; 

import java.util.HashMap; 

import common.AuthenticationMessage; 

import server.ChatUser; 

import server.Connection; 

/**
 * TODO description
 */
public   class  Server {
	
	
	public int port;

	
	
	public static void main(String args[]) throws IOException {
		if (args.length != 1)
			throw new RuntimeException("Syntax: ChatServer <port>");	
		int port = Integer.parseInt(args[0]);
	
		new Server(port, args);
			
	}

	
	
	/**
	 * awaits incoming connections and creates Connection objects accordingly.
	 * 
	 * @param port
	 *            port to listen on
	 */
	public Server(int port, String[] args) throws IOException {
		this.port = port;
		
		initStuff();
		
		ServerSocket server = new ServerSocket(port);
		while (true) {
			System.out.println("Waiting for Connections...");
			Socket client = server.accept();
			System.out.println("Accepted from " + client.getInetAddress());
			Connection c = connectTo(client);
			c.start();
		}
	}

	
	
	 private void  initStuff__wrappee___Base  (){
		System.out.println("Danach");
	}

	

	 private void  initStuff__wrappee__Logging  () {
		blacklist = new ArrayList<String>();
		blacklist.add("Arsch");
		blacklist.add("Spam");
		blacklist.add("Microsoft");
		blacklist.add("CDU");
		
		initStuff__wrappee___Base();
	}

	
	
	private void initStuff(){
		validUsers.add(new ChatUser(
						"meye",
						new AuthenticationMessage("", "HalloWelt").getPasswordHash(),
						"Rene"
		));
		validUsers.add(new ChatUser(
						"tommy",
						new AuthenticationMessage("", "1").getPasswordHash(),
						"Tommy"
		));
		validUsers.add(new ChatUser(
				"alice",
				new AuthenticationMessage("", "1").getPasswordHash(),
				"Alice"
		));
		
		initStuff__wrappee__Logging();


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
	public void removeConnection(Connection connection) {
		connections.remove(connection);
	}

	
	public ArrayList<String> blacklist;

	
	private Vector<ChatUser> validUsers = new Vector<ChatUser>();

	
	public HashMap<Connection, Boolean> authenticatedConnections = new HashMap<Connection, Boolean>();

	
	public HashMap<Connection, ChatUser> connectedUsers = new HashMap<Connection, ChatUser>();

	
	
	public ChatUser authenticate(AuthenticationMessage authenticationMessage) {
		for (ChatUser validUser : validUsers) {
			System.out.println("Checking: "+authenticationMessage.getUsername() +" && "+validUser.username);
			System.out.println("Message Hash:"+authenticationMessage.getPasswordHash());
			System.out.println("Valid User:"+validUser.passwordHash);
			System.out.println("---");
			if(validUser.username.equals(authenticationMessage.getUsername())
					&& validUser.passwordHash.equals(authenticationMessage.getPasswordHash())){
				return validUser;
			}
		}
		
		return null;
	}

	
	public void sendToRealName(String realName, String text) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				if(connection.user != null && connection.user.realName.equalsIgnoreCase(realName))
					connection.send(text);
			}
		}
	}


}
