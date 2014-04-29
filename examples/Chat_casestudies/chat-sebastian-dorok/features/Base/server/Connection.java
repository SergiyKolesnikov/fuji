package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import common.message.AuthMessage;
import common.message.StatusMessage;
import common.message.AbstractMessage;
import common.message.StatusMessage.STATUS;
import common.message.TextMessage;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	protected Socket socket;
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	private Server server;
	private String username;

	public Connection(Socket s, Server server) {
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
		// send connection successful state
		send(new StatusMessage("Connection succesfully established.",
				STATUS.CONNECT_SUCC));
		try {
			Object msg = null;
			// readObject blocks until a new object is available
			while ((msg = inputStream.readObject()) != null) {
				handleIncomingMessage(msg);
			}
		} catch (SocketException e) {
		} catch (EOFException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.removeConnection(this);
			if (this.username != null) {
				server.broadcast(new StatusMessage(username + " has left.",
						STATUS.USER_LEFT));
			}
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
	private void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			((TextMessage) msg).setFrom(this.username);
			server.broadcast((AbstractMessage) msg);
		}
		if (msg instanceof AuthMessage) {
			AuthMessage m = (AuthMessage) msg;
			this.username = m.getUser();
			server.broadcast(new StatusMessage(username + " has joined.",
					STATUS.USER_JOINED));
		}
	}

	/**
	 * sends a message to the client
	 * 
	 */
	public void send(AbstractMessage msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getUserName() {
		return this.username;
	}
}
