package server; 

import java.io.EOFException; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.net.SocketException; 

import common.TextMessage; 

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public  class  Connection  extends Thread {
	
	protected Socket socket;
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;	
	private Server server;
	
	private String name = null;

	public Connection(Socket s, Server server) {
		this.socket = s;
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.server = server;
		this.name = getClientName();
	}
	
	private String getClientName() {
		if(this.name == null)
			return socket.getInetAddress().toString();
		else
			return name;
	}
	
	private boolean setClientName(TextMessage msg) {
		 return false;
	}

	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		String clientName = getClientName();
		try {
			server.broadcast(new TextMessage("has joined.", clientName));
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
			server.broadcast(new TextMessage("has left.", clientName));
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
			TextMessage tm = (TextMessage) msg;
			tm.setSource(getClientName());
			if(!setClientName(tm))
				server.broadcast(tm);
		}
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


}
