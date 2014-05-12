
package NewEquation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;





abstract class Client$$Chatbasic implements Runnable {

	public static void main(String[] args) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		Client client = new Client(args[0], Integer.parseInt(args[1]));
	}

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;

	Client$$Chatbasic(String host, int port) {
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
		userInterfaceinit();
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
			fireAddLine((TextMessage) msg);
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
	public void fireAddLine(TextMessage msg) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(msg);
		}
				
	}
	/**
	 * Initialisierung des Userinterface 
	 */
	public void userInterfaceinit(){
		System.out.println("UI Initialisieren");
	}


	public void stop() {
		thread.stop();
	}

}



abstract class Client$$Gui extends  Client$$Chatbasic  {
	
	
	public void userInterfaceinit(){
		super.userInterfaceinit();
		new Gui(((Client) this));
	}
      // inherited constructors



	Client$$Gui ( String host, int port ) { super(host, port); }
}



abstract class Client$$Konsole extends  Client$$Gui  {
	
	
	public void userInterfaceinit(){
		super.userInterfaceinit();
		new Konsole(((Client) this));
	}
      // inherited constructors



	Client$$Konsole ( String host, int port ) { super(host, port); }
}



abstract class Client$$Historie extends  Client$$Konsole  {

	public void fireAddLine(TextMessage msg){
		msg.log("e:/Clientlog.dat");	
		super.fireAddLine(msg);
	}
      // inherited constructors



	Client$$Historie ( String host, int port ) { super(host, port); }
}



public class Client extends  Client$$Historie  {

	public void fireAddLine(TextMessage msg){
		msg.entverschieben();
		super.fireAddLine(msg);
	}
	
	public void send(TextMessage msg){
		msg.verschieben();
		super.send(msg);
	}
      // inherited constructors



	Client ( String host, int port ) { super(host, port); }

}