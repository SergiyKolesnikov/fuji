
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Toolkit;
import java.awt.AWTError;



abstract class Client$$Basis implements Runnable {
	
	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
		new Client(args[0], Integer.parseInt(args[1]));
	}
	
	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;

	public Client$$Basis(String host, int port) {
		set();
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
	
	protected void set() {}

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
	 * @param msg the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage message = (TextMessage) msg;
			process(message);
			fireAddLine(message);
		}
	}
	
	protected void process(TextMessage msg) {}	

    protected void prepare(TextMessage msg) {}

	public void send(String line) {
		send(new TextMessage(line, "localhost"));
	}

	public void send(TextMessage msg) {
		prepare(msg);
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

	public void stop() {
		thread.stop();
	}
}

abstract class Client$$Gui extends  Client$$Basis  {

	protected void set() {
		super.set();
		new Gui("localhost", ((Client) this));
	}
      // inherited constructors



	public Client$$Gui ( String host, int port ) { super(host, port); }	
}

abstract class Client$$Invertiere_Nachrichteninhalt extends  Client$$Gui  {
	
	private final InvertiereNachrichteninhalt encrypter = new InvertiereNachrichteninhalt();

	protected void process(TextMessage msg) {
		if(msg.getSender().equals("Server")) {
			super.process(msg); 
		} else {	
			String content = msg.getContent();
			content = encrypter.decrypt(content);
			msg.setContent(content);
			super.process(msg);
		}	
	}	
	
	protected void prepare(TextMessage msg) {
		String content = msg.getContent();
		content = encrypter.encrypt(content);
		msg.setContent(content);
		super.prepare(msg);
	}
      // inherited constructors



	public Client$$Invertiere_Nachrichteninhalt ( String host, int port ) { super(host, port); }	
}

abstract class Client$$Vertausche_Buchsaben extends  Client$$Invertiere_Nachrichteninhalt  {
	
	private final VertauscheBuchstaben encrypter = new VertauscheBuchstaben();

	protected void process(TextMessage msg) {
		if(msg.getSender().equals("Server")) {
			super.process(msg); 
		} else {	
			String content = msg.getContent();
			content = encrypter.decrypt(content);
			msg.setContent(content);
			super.process(msg);
		}		
	}	
	
	protected void prepare(TextMessage msg) {
		String content = msg.getContent();
		content = encrypter.encrypt(content);
		msg.setContent(content);
		super.prepare(msg);
	}
      // inherited constructors



	public Client$$Vertausche_Buchsaben ( String host, int port ) { super(host, port); }	
}

abstract class Client$$Historie extends  Client$$Vertausche_Buchsaben  {
	
	private final HistorieLogger logger = new HistorieLogger(Client.class.getName() + ".txt");

	protected void process(TextMessage msg) {
		super.process(msg);
		logger.log(msg.toString());
	}
      // inherited constructors



	public Client$$Historie ( String host, int port ) { super(host, port); }	
}



abstract class Client$$Soundausgabe extends  Client$$Historie  {
	
	protected void process(TextMessage msg) {
		try {
			Toolkit.getDefaultToolkit().beep();
		} catch (AWTError e) { 
			System.err.println(e.getMessage());
		}		
		super.process(msg);
	}
      // inherited constructors



	public Client$$Soundausgabe ( String host, int port ) { super(host, port); }	
}

public class Client extends  Client$$Soundausgabe  {

	public void fireAddLine(TextMessage msg) {
		if (Spamfilter.hasSpamHint(msg)) {
			System.out.println("dubious message was discarded..."); 
		} else {
			super.fireAddLine(msg);
		}		
	}
      // inherited constructors



	public Client ( String host, int port ) { super(host, port); }
}