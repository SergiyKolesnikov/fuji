package server; 

import java.io.EOFException; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.net.SocketException; 
import java.sql.Timestamp; 
import java.util.Date; 
import common.*; 

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public  class  Connection  extends Thread {
	
	protected Socket socket;

	
	protected ObjectInputStream inputStream;

	
	protected ObjectOutputStream outputStream;

	
	private Server server;

	

	public Connection(Socket s, Server server) 
	{
		this.socket = s;
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
			//#if Authentification
			
			String pwd = (String)inputStream.readObject();
			if (!pwd.equals("UnH4cK4b3Lp455W0rD")) {
				// send false as confirmation of connection rejection
				outputStream.writeBoolean(false);
				outputStream.flush();
			}
			//#endif
			
			// send true as confirmation of connection acceptance
			outputStream.writeBoolean(true);
			outputStream.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//#if Authentification			
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//#endif
		this.server = server;
	}

	

	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		String clientName = socket.getInetAddress().toString();
		try {
			server.broadcast(new TextMessage(clientName + " has joined.", "Server"));
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
			server.broadcast(new TextMessage(clientName + " has left.", "Server"));
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
		if (msg instanceof ITextMessage) {
			
			//Feature: MessageHistory
			addToHistory((ITextMessage) msg);
			
			//Feature: Spamfilter
			if (containsSpam((ITextMessage)msg)) return;
			
			server.broadcast((ITextMessage)msg);
			//(name + " - " + ((ITextMessage) msg).getContent());
		}
	}

	

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String line) {
		send(new TextMessage(line, "Server"));
	}

	

	public void send(ITextMessage msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
		}
	}

	
	
	//Feature: MessageHistory
	public void addToHistory(ITextMessage msg){	}

	
	
	//Feature: Spamfilter
	private boolean containsSpam(ITextMessage msg){	return false; }


}
