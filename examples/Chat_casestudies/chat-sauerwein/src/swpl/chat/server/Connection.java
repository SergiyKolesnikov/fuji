package swpl.chat.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import swpl.chat.common.Host;
import swpl.chat.common.HostType;
import swpl.chat.common.TextMessage;
import swpl.chat.common.Logger;
import swpl.chat.common.Encryption;
import swpl.chat.common.DefaultEncryption;



/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
abstract class Connection$$Chat$swpl$chat$server extends Thread implements Host {
	protected Socket socket;

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Server server;
	
	private boolean connectionOpen = true;

	public Connection$$Chat$swpl$chat$server(Socket s, Server server) {
		this.socket = s;
		
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.server = server;
	}

	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		String clientName = socket.getInetAddress().toString();
		try {
			server.broadcast(new TextMessage(clientName + " has joined."));
			
			while (connectionOpen) {
				try {
					Object msg = inputStream.readObject();
					handleIncomingMessage(clientName, msg);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			server.removeConnection(((Connection) this));
			server.broadcast(new TextMessage(clientName + " has left."));
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	protected void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage textMsg = (TextMessage) msg;

			server.broadcast(prepareReceiveMessage(textMsg));
		}
	}
	
	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String line) {
		send(new TextMessage(line));
	}

	public void send(TextMessage msg) {
		try {
			msg = prepareSendMessage(msg);
			
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
			connectionOpen = false;
		}

	}
	
	protected TextMessage prepareReceiveMessage(TextMessage msg) {
		return msg;
	}
	
	protected TextMessage prepareSendMessage(TextMessage msg) {
		return msg;
	}

	public String getHostName() {
		return socket.getInetAddress().toString();
	}

	public HostType getHostType() {
		return HostType.Client;
	}

}



abstract class Connection$$Authentication$swpl$chat$server extends  Connection$$Chat$swpl$chat$server  {
	private static final String PASSWORD = "secret";
	
	private boolean loggedIn = false;
	
	protected TextMessage prepareReceiveMessage(TextMessage msg) {		
		// if authenticated, just forward the message
		if (loggedIn) {
			return super.prepareReceiveMessage(msg);
		}
		
		
		// client is not authenticated, check if the sent message is the password
		if (isValidPassword(msg.getContent())) {
			loggedIn = true;
			send("Successfully logged in");
		} else {
			send("Sorry, wrong password");
		}
		
		throw new SecurityException("not authenticated");
	}
	
	protected void handleIncomingMessage(String name, Object msg) {
		try {
			super.handleIncomingMessage(name, msg);
		} catch (SecurityException exc) {
			// don't let the other features see this message
		}
	}
	private boolean isValidPassword(String password) {
		return password.equals(PASSWORD);
	}
      // inherited constructors



	public Connection$$Authentication$swpl$chat$server ( Socket s, Server server ) { super(s, server); }
}



abstract class Connection$$History$swpl$chat$server extends  Connection$$Authentication$swpl$chat$server  {
	
	private Logger logger = new Logger("server");
	
	protected TextMessage prepareReceiveMessage(TextMessage msg) {		
		logger.log(getHostName() + " -> " + server.getHostName() + ": "
					+ msg.getContent());
		
		return super.prepareReceiveMessage(msg);
	}
	
	protected TextMessage prepareSendMessage(TextMessage msg) {
		msg = super.prepareSendMessage(msg);
		
		logger.log(server.getHostName() + " -> " + getHostName() + ": "
					+ msg.getContent());
		
		return msg;
	}
      // inherited constructors



	public Connection$$History$swpl$chat$server ( Socket s, Server server ) { super(s, server); }
}



public class Connection extends  Connection$$History$swpl$chat$server  {
	private Encryption encryption = new DefaultEncryption();
	
	protected TextMessage prepareReceiveMessage(TextMessage msg) {
		String decodedText = encryption.decode(msg.getContent());
		msg.setContent(decodedText);
		
		return super.prepareReceiveMessage(msg);
	}
	
	protected TextMessage prepareSendMessage(TextMessage msg) {
		msg = super.prepareSendMessage(msg);
		
		String encodedText = encryption.encode(msg.getContent());
		msg.setContent(encodedText);
		
		return msg;
	}
      // inherited constructors



	public Connection ( Socket s, Server server ) { super(s, server); }
}