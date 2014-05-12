package NewEquation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;



abstract class Client$$Base implements Runnable {

	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
		new Client(args[0], Integer.parseInt(args[1]));
	}
	
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	protected Thread thread;
	
	public Client$$Base(String host, int port) {
		try {
			System.out.println("Connecting to " + host + " (port " + port + ")...");
			Socket s = new Socket(host, port);
			this.outputStream = new ObjectOutputStream((s.getOutputStream()));
			this.inputStream = new ObjectInputStream((s.getInputStream()));
			thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		try {
			while (true) {
				try {
					Object msg = inputStream.readObject();
					handleIncomingMessage(msg);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			thread = null;
			try {
				outputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			String message = ((TextMessage) msg).getContent();
			
			// über neue Nachricht informieren
			fireAddLine(message);
		}
	}

	public void send(String msg) {
		send(new TextMessage(msg));
	}

	public void send(TextMessage msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			thread.stop();
		}
	}

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(String line) {
	
	}

	public void stop() {
		thread.stop();
	}
}



abstract class Client$$GUI extends  Client$$Base  {

	private Gui gui;
	
	public void run() {
		gui = new Gui(((Client) this));
		super.run();	
	}
	
	public void fireAddLine(String line) {
		super.fireAddLine(line);
		gui.onMessageReceived(line);
	}
      // inherited constructors


	
	public Client$$GUI ( String host, int port ) { super(host, port); }
}



abstract class Client$$History extends  Client$$GUI  {

	History history;

	public void run() {
		history = new History("Client.txt");
		super.run();
	}	

	public void fireAddLine(String line) {
		super.fireAddLine(line);
		history.onMessageReceived(line);
	}
      // inherited constructors


	
	public Client$$History ( String host, int port ) { super(host, port); }

}



abstract class Client$$Login extends  Client$$History  {

	String password = "PASSWORD";

	public void run() {
		send(password);
		super.run();	
	}
      // inherited constructors


	
	public Client$$Login ( String host, int port ) { super(host, port); }
}



abstract class Client$$Reverse extends  Client$$Login  {
	
	ReverseEncryption crypt = new ReverseEncryption();
	
	public void send(String msg) {
		super.send(crypt.getEncryptedMessage(msg));		
	}
	
	public void fireAddLine(String line) {
		super.fireAddLine(crypt.getDecryptedMessage(line));
	}
      // inherited constructors


	
	public Client$$Reverse ( String host, int port ) { super(host, port); }
}



public class Client extends  Client$$Reverse  {
	
	CaesarEncryption crypt = new CaesarEncryption();
	
	public void send(String msg) {
		super.send(crypt.getEncryptedMessage(msg));
	}
	
	public void fireAddLine(String line) {
		super.fireAddLine(crypt.getDecryptedMessage(line));
	}
      // inherited constructors


	
	public Client ( String host, int port ) { super(host, port); }
}