

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;



/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
abstract class Connection$$Base extends Thread {
	
	protected Socket socket;

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Server server;
	private boolean connectionOpen = true;

	public Connection$$Base(Socket s, Server server) {
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
			server.broadcast(clientName + " has joined.");
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
			server.broadcast(clientName + " has left.");
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
		if (msg instanceof TextMessage){
			String text=((TextMessage)msg).getContent();
			server.broadcast(name + " - " + text);
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
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
			connectionOpen = false;
		}

	}

}



abstract class Connection$$Encryption extends  Connection$$Base  {

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String line) {
		String text=Encryption.encrypt(line);
		super.send(text);
	}
      // inherited constructors



	public Connection$$Encryption ( Socket s, Server server ) { super(s, server); }

}



abstract class Connection$$Authentification extends  Connection$$Encryption  {
	
	private boolean communicationAllowed=false;
	private String password="MyPassword";
	
	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	protected void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage){
			String text=((TextMessage)msg).getContent();
			if(!communicationAllowed){
				communicationAllowed=password.equals(text);
				if(communicationAllowed)
					server.broadcast(name + " has joined.");
				else
					sendWithoutAuthentification("You have to enter the password first!");
			}
			else
				server.broadcast(name + " - " + text);
		}
	}
	
	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String line) {
		if(communicationAllowed)
			super.send(line);
	}
	
	/**
	 * sends a message to the client without authentification
	 * 
	 * @param line
	 *            text of the message
	 */
	public void sendWithoutAuthentification(String line) {
		super.send(line);
	}
      // inherited constructors



	public Connection$$Authentification ( Socket s, Server server ) { super(s, server); }

}



public class Connection extends  Connection$$Authentification  {
	
	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	protected void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage){
			String text=((TextMessage)msg).getContent();
			text=Encryption.decrypt(text);
			super.handleIncomingMessage(name, new TextMessage(text));
		}
	}
      // inherited constructors



	public Connection ( Socket s, Server server ) { super(s, server); }

}