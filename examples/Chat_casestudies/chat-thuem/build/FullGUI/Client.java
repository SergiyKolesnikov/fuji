package FullGUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;




/**
 * simple chat client
 */
abstract class Client$$Chat implements Runnable {
	
	private Log log = new Log();
	
	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		new Client(args[0], Integer.parseInt(args[1]));
	}

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;

	public Client$$Chat(String host, int port) {
		init(host, port);
	}

	protected void init(String host, int port) {
		try {
			System.out.println("Connecting to " + host + " (port " + port
					+ ")...");
			Socket s = new Socket(host, port);
			((Client) this).outputStream = new ObjectOutputStream((s.getOutputStream()));
			((Client) this).inputStream = new ObjectInputStream((s.getInputStream()));
			thread = new Thread(((Client) this));
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
		log.write(line);
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(line);
		}
	}

	public void stop() {
		thread.stop();
	}

}




/**
 * simple chat client
 */
abstract class Client$$Authentification extends  Client$$Chat  implements Runnable {
	
	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		try {
			outputStream.writeObject("Hossa");
			outputStream.flush();
			super.run();
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
      // inherited constructors



	public Client$$Authentification ( String host, int port ) { super(host, port); }

}




/**
 * simple chat client
 */
public class Client extends  Client$$Authentification  implements Runnable {
	
	protected void init(String host, int port) {
		super.init(host, port);
		new Gui("Chat " + host + ":" + port, ((Client) this));
	}
      // inherited constructors



	public Client ( String host, int port ) { super(host, port); }

}