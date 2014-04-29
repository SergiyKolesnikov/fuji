
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;


/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	protected Socket socket;
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	private Server server;

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
		String clientName = socket.getInetAddress().toString();
		try {
			server.broadcast(server.createServerMessage(clientName
					+ " has joined."));
			Object msg = null;
			while ((msg = inputStream.readObject()) != null) {
				handleIncomingMessage(clientName, msg);
			}
		} catch (SocketException e) {
		} catch (EOFException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.removeConnection(this);
			server.broadcast(server.createServerMessage(clientName
					+ " has left."));
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
	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof Message) {
			Message message = (Message) msg;
			handleMessage(message);
			//broadCast(message);
		} else {
			handleUnknownMessage(msg);
		}
	}
	
	private void handleUnknownMessage(Object msg){
		System.out.println("Server: Unknown Message");
	}
	
	private void handleMessage(Message msg){
		broadCast(msg);
	}

	private void broadCast(Message msg) {
		server.broadcast(msg);
	}

	/*
	private void handleAuthenticationSuccessfull(String name, Message msg) {
		// forward Message
		// server.broadcast(msg);
		broadCast(msg);
	}*/

	// #if Authentication
//@	private void handleAuthenticationFailed(String name) {
//@		this.send(server.createServerMessage("authentication failed"));
//@	}
//@
//@	private boolean checkAuthentication(SecuredMessage msg) {
//@		return (msg.getPassword().equals(Config.PASSWORD));
//@	}
	// #endif
	
	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */

	/*
	public void send(ContentMessage msg) {
		send((Message)msg);
	}*/
	
	public void send(Message msg) {
		// SecuredMessage smsg = new SecuredMessage(msg, Static.password);
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
		}
	}
}
