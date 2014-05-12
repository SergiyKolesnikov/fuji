package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.TextMessage;
import common.Crypter;



/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
abstract class Connection$$Base$server extends Thread {
	protected Socket socket;

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	private Server server;
	private boolean connectionOpen = true;

	public Connection$$Base$server(Socket s, Server server) {
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
		if (msg instanceof TextMessage)
			server.broadcast(name + " - " + ((TextMessage) msg).getContent());
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

	protected void send(TextMessage msg) {
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


abstract class Connection$$Auth$server extends  Connection$$Base$server  {
	
	private boolean activated = false;
	
	private String password = "LaLeLu";
	
	protected void handleIncomingMessage(String name, Object msg) {
		if (activated) {
			super.handleIncomingMessage(name, msg);
		} else if (msg instanceof TextMessage) {
			if (((TextMessage)msg).getContent().equals(password)) {
				activated = true;
			}
		}
	}
      // inherited constructors



	public Connection$$Auth$server ( Socket s, Server server ) { super(s, server); }
}


/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends  Connection$$Auth$server  {

	private Crypter crypter = new Crypter();
	
	protected void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage) {
			super.handleIncomingMessage(name, crypter.decrypt((TextMessage) msg));
		}
	}
	
	protected void send(TextMessage msg) {
		super.send(crypter.encrypt(msg));
	}
      // inherited constructors



	public Connection ( Socket s, Server server ) { super(s, server); }
}