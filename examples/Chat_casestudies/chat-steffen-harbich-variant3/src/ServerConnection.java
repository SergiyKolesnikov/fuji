import java.net.Socket; 
import java.util.HashMap; 
import java.util.HashSet; 
import java.util.Map; 
import java.util.Set; 

public   class  ServerConnection  extends Connection {
	

	private Server server;

	

	public ServerConnection(Socket s, Server server) {
		super(s);
		this.server = server;
	}

	

	/**
	 * waits for incoming messages from the socket
	 */
	protected void  runCommunicationLoop__wrappee__GUI  () {
		try {
			// if no plugin has set the user name then we do here
			if (userName == null)
				userName = socket.getInetAddress().toString();
			
			// inform others about entering chat
			server.broadcast(MessageFactory.newTextMessage("" + userName + " has joined."));
			
			// do chatting
			while (connectionOpen) {
				Message msg = receive();
				
				if (msg != null)
					handleIncomingMessage(msg);
			}
		} 
		finally {
			// clean up
			server.removeConnection(this);
			server.broadcast(MessageFactory.newTextMessage("" + userName + " has left."));
			
			close();
		}
	}

	
	
	protected void runCommunicationLoop() {
		connectionEstablished();
		runCommunicationLoop__wrappee__GUI();
		connectionClosed();
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(Message msg) {
		if (msg instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) msg;
			txtMsg.setContent(getUserName() + " - " + txtMsg.getContent());

			server.broadcast(txtMsg);
		}
	}

	

	private static Map<String, String> userNameToPassphraseMap;

	
	private static Set<String> connectedUsers = new HashSet<String>();

	
	
	static {
		userNameToPassphraseMap = new HashMap<String, String>();
		userNameToPassphraseMap.put("Test1", "Bingo");
		userNameToPassphraseMap.put("Test2", "Password");
	}

	

	private boolean isAuthorized(AuthenticationMessage msg) {
		if (userNameToPassphraseMap.containsKey(msg.getUserName()) == false)
			return false;
		
		if (userNameToPassphraseMap.get(msg.getUserName()).equals(msg.getPassPhrase()) == false)
			return false;
		
		if (connectedUsers.contains(msg.getUserName()) == true)
			return false;
		
		return true;
	}

	

	private synchronized void connectionEstablished() {
		// at the server we wait for a message from client that contains
		// authentication information
		
		Message msg = receive();
		
		if (msg instanceof AuthenticationMessage) {
			AuthenticationMessage authMsg = (AuthenticationMessage) msg;
			
			if (isAuthorized(authMsg)) {
				connectedUsers.add(authMsg.getUserName());
				setUserName(authMsg.getUserName());
				send(new AuthenticationResultMessage(true));
			}
			else {
				send(new AuthenticationResultMessage(false));
				close();
			}
		}
		else if (msg != null) {
			send(MessageFactory.newTextMessage("Error: authentication required!"));
			close();
		}
	}

	
	
	private synchronized void connectionClosed() {
		connectedUsers.remove(getUserName());
	}


}
