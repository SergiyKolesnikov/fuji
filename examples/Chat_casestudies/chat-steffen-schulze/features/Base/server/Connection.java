package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import server.Server;

import common.*;

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
	
	public String getUsername()
	{
		return username;
	}

	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		String clientName = socket.getInetAddress().getHostAddress();
		try {
			
			this.username = readString();
			if (!IsAuthValid(this.username)) return;
			
			server.broadcast(Server.NAME, clientName + " has joined.");
			server.broadcast(Server.NAME, "Hello " + this.username);
			server.users.add(Users.getUser(username));
	
			Object msg = null;
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
			server.broadcast(Server.NAME, clientName + " has left.");
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
		if (msg instanceof TextMessage)
			server.broadcast(((TextMessage) msg).getSender(), ((TextMessage) msg).getContent());
	}

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String sender, String line) {
		send(new TextMessage(sender, line));
	}

	public void send(TextMessage msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
		}
	}
	
	public void sendUserList(Object list)
	{
		try {
			synchronized (outputStream) {
				outputStream.writeObject(list);
			}
			outputStream.flush();
		} catch (IOException ex) {
		}
	}
	
	private String readString() throws ClassNotFoundException, IOException 
	{
		Object obj = inputStream.readObject();
		return obj.toString();
	}
	
	protected boolean IsAuthValid(String userName) throws ClassNotFoundException, IOException
	{
		return true;
	}
}
