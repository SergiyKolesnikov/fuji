package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import common.TextMessage;
import common.Crypter;
import common.HistoryWriter;



/**
 * simple chat client
 */
abstract class Client$$Base$client implements Runnable {
	
	protected static Client client;
	
	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
		client = new Client(args[0], Integer.parseInt(args[1]));
	}

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;

	public Client$$Base$client(String host, int port) {
		try {
			System.out.println("Connecting to " + host + " (port " + port
					+ ")...");
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
			fireAddLine(((TextMessage) msg).getContent() + "\n");
		}
	}

	protected void send(String line) {
		send(new TextMessage(line));
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
	 * listener-list for the observer pattern
	 */
	private ArrayList listeners = new ArrayList();

	/**
	 * addListner method for the observer pattern
	 */
	public void addLineListener(ChatLineListener listener) {
		listeners.add(listener);
	}

	/**
	 * removeListner method for the observer pattern
	 */
	public void removeLineListener(ChatLineListener listner) {
		listeners.remove(listner);
	}

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(String line) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(line);
		}
	}

	public void stop() {
		thread.stop();
	}

}

abstract class Client$$GUI$client extends  Client$$Base$client  implements Runnable {
	public static void main(String args[]) throws IOException {
		Client$$Base$client.main(args);
		new Gui("Chat " + "localhost" + ":" + "8081", client);
	}
      // inherited constructors



	public Client$$GUI$client ( String host, int port ) { super(host, port); }
}

abstract class Client$$Auth$client extends  Client$$GUI$client  {
	
	private String password = "LaLeLu"; 
	
	public void run() {
		send(new TextMessage(password));
		super.run();	
	}
      // inherited constructors



	public Client$$Auth$client ( String host, int port ) { super(host, port); }	
}


/**
 * simple chat client
 */
abstract class Client$$Crypter$client extends  Client$$Auth$client  {
	
	private Crypter c = new Crypter();
	
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			super.handleIncomingMessage(c.decrypt((TextMessage) msg));
		}	
	}
	
	public void send(TextMessage msg) {
		super.send(c.encrypt(msg));
	}
      // inherited constructors



	public Client$$Crypter$client ( String host, int port ) { super(host, port); }
}


/**
 * simple chat client
 */
public class Client extends  Client$$Crypter$client  {
	
	private static HistoryWriter w;

	public static void main(String args[]) throws IOException {
		w = new HistoryWriter("Client_" + args[0] + args[1] + "_");
		Client$$Crypter$client.main(args);	
	}
	public void fireAddLine(String line) {
		w.write(line);
		super.fireAddLine(line);
	}
      // inherited constructors



	public Client ( String host, int port ) { super(host, port); }
}