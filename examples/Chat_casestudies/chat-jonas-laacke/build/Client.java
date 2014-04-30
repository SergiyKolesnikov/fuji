

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * simple chat client
 */
abstract class Client$$Base implements Runnable {

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
	}
	
	public Client$$Base(String host, int port) {
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
			String message=((TextMessage) msg).getContent();
			fireAddLine(message + "\n");
		}
	}
	
	public void send(String line) {
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



abstract class Client$$GUI extends  Client$$Base {
	
	public static void main(String[] args) throws IOException {
		Client$$Base.main(args);
		Client client = new Client(args[0], Integer.parseInt(args[1]));
		new GUI("Chat " + args[0] + ":" + args[1], client);
	}
      // inherited constructors


	
	public Client$$GUI ( String host, int port ) { super(host, port); }

}



abstract class Client$$Encryption extends  Client$$GUI  {
	
	public void send(String line) {
		String message=Encryption.encrypt(line);
		super.send(message);
	}
      // inherited constructors


	
	public Client$$Encryption ( String host, int port ) { super(host, port); }

}



abstract class Client$$Color extends  Client$$Encryption  {
	
	private String color="";
	
	public void send(String line) {
		//color selected by user
		if(line.startsWith("/color")) {
			String[] switchColor=line.split(" ");
			color=switchColor[1]+": ";
		}
		else{
			super.send(color+line);
		}
	}
      // inherited constructors


	
	public Client$$Color ( String host, int port ) { super(host, port); }

}



abstract class Client$$History extends  Client$$Color  {
	
	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			String message=((TextMessage) msg).getContent();
			History.saveMessage(message);
			super.handleIncomingMessage(msg);
		}
	}
      // inherited constructors


	
	public Client$$History ( String host, int port ) { super(host, port); }

}



public class Client extends  Client$$History  {
	
	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			String message=Encryption.decrypt(((TextMessage) msg).getContent());
			super.handleIncomingMessage(new TextMessage(message));
		}
	}
      // inherited constructors


	
	public Client ( String host, int port ) { super(host, port); }

}