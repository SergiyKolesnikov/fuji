package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import server.Server;


import common.Message;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	protected Socket socket;
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	private Server server;
	private String clientName;

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
		 this.clientName = socket.getInetAddress().toString();
		try {
			server.broadcast(new Message(clientName + " has joined.",(byte) 0x00));
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
			server.broadcast(new Message(clientName + " has left.",(byte) 0x00));
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
		if (msg instanceof Message){
			Message tmsg = (Message) msg;
			// parse message for chatcommands
			// change nickname, min. 4 to 32 chars long
			String pattern = "(/nick )(\\w{4,32})";
			if (tmsg.getText().matches(pattern)){
				this.setclientName(tmsg.getText().replaceAll(pattern, "$1"));
				return;
			}
			// private Message
			pattern = "(/msg )(\\w{4,32}) (.+)";
			if (tmsg.getText().matches(pattern)){
				server.unicast(new Message(tmsg.getText().replaceAll(pattern, "$2"), tmsg.getStatus(), null , tmsg.getText().replaceAll(pattern, "$1")));
				return;
			}
			server.broadcast(tmsg);
		}
	}

	/**
	 * sends a plain message to the client with Status 0x00
	 * 
	 * @param text
	 *            Text of the message
	 */
	public void send(String text) {
		send(new Message(text, (byte) 0x00));
	}

	public void send(Message msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void setclientName(String str){
		this.clientName = str;
	}
	public String getclientName() {
		return this.clientName;
	}
}
