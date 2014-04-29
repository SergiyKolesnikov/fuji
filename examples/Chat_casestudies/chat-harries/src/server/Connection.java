package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import common.Log;

import common.TextMessage;



/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
abstract class Connection$$Base$server extends Thread {
	protected Socket socket;

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;
	private Server server;
	public boolean connectionOpen = true;

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
			//server.broadcast(clientName + " has joined.");
			while (connectionOpen) {

				try {
					Object msg = inputStream.readObject();
					handleIncomingMessage(msg);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			server.removeConnection(((Connection) this));
			//server.broadcast(clientName + " has left.");
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
	 * @param msg
	 *            received message
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			server.broadcast(((TextMessage) msg).getContent());
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



public class Connection extends  Connection$$Base$server  {
	
	protected void handleIncomingMessage(Object msg) {
		super.handleIncomingMessage(msg);
		if (msg instanceof TextMessage) {
			Log.info(((TextMessage) msg).getContent());
		}
	}
      // inherited constructors



	public Connection ( Socket s, Server server ) { super(s, server); }

}