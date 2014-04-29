
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Calendar;



abstract class Client$$Base implements Runnable {
	
	//fields
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	protected Thread thread;
	protected static String username;
	
	private static Encrypter enc = null;
	
	//main
	public static void main(String args[]) throws IOException {
		if (args.length != 4)
			throw new RuntimeException(
					"Syntax: ChatClient <host> <port> [gui/console] [rot13/scramble2]");

		//command line parameter to choose encryption
		if (args[3].equals("rot13") || args[3].equals("scramble2")) {
			String encType = args[3];
			enc = new Encrypter(encType);
		}
		
		Client client = new Client(args[0], Integer.parseInt(args[1]), enc);
		username = args[0] + ":" + args[1];

		//gui or console?
		if (args[2].equals("gui")) {
			new Gui("Chat " + username, client);
		} else {
			new Console(client);
		}

	}

	//modified 15.05: gets Encrypter object that is being used for encryption during runtime 
	public Client$$Base(String host, int port, Encrypter enc) {
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
			Thread thisthread = Thread.currentThread();
			while (thread == thisthread) {
				try {
					Object msg = inputStream.readObject();
					handleIncomingMessage(msg);
				} catch (EOFException e) {
					e.printStackTrace();
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
		} //added 15.05: handling of encrypted Messages
		if (msg instanceof EncryptedMessage) {
			EncryptedMessage encMsg = (EncryptedMessage) msg;
			fireAddLine( enc.decrypt(encMsg) + "\n" );
		}
	}

	//modified: 15.05.: handling of encrypted Messages
	//Feature: privateMessaging: erzeuge falls nÃÂ¶tig eine privateMessage
	//und versende diese (beginnnen mit /msg <user>
	// es gibt auch registerMessages um sich zu registrieren beginnen mit / <user>
	public void send(String line) {
		
		Message msg = createMessage(line);
		send(msg);
	}

	protected Message createMessage(String line) {
		
		Message msg = new TextMessage(line);
		
		if (enc != null) {
			EncryptedMessage encMsg = enc.encrypt(msg);
			msg = encMsg;
		}
		
		return msg;
	}

	//modified 13.05: changed from TextMessage msg to Message msg
	public void send(Message msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			((Client) this).stop();
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
		thread = null;
	}
	
}

abstract class Client$$ColorChat extends  Client$$Base  {
	
	protected String currentColor = "black";
	
	public void setCurrentColor(String color) {
		((Client) this).currentColor = color;
	}
	
	public String getCurrentColor() {
		return ((Client) this).currentColor;
	}
      // inherited constructors



	//modified 15.05: gets Encrypter object that is being used for encryption during runtime 
	public Client$$ColorChat ( String host, int port, Encrypter enc ) { super(host, port, enc); }

}

abstract class Client$$Authentification extends  Client$$ColorChat  {
	
	public void login(String password) {
		send(new AuthMessage(username, password));
	}
      // inherited constructors



	//modified 15.05: gets Encrypter object that is being used for encryption during runtime 
	public Client$$Authentification ( String host, int port, Encrypter enc ) { super(host, port, enc); }

}

abstract class Client$$History extends  Client$$Authentification  {
	
	protected File history;
	protected BufferedWriter historyWriter;

	//modified 15.05: gets Encrypter object that is being used for encryption during runtime 
	public Client$$History(String host, int port, Encrypter enc) { super(host, port, enc); 

      	
      try {
		new File("ClientLogs" + System.getProperty("file.separator")).mkdir();
		history = new File("ClientLogs" + System.getProperty("file.separator") + "history_" 
					+ System.currentTimeMillis() / 1000);
		historyWriter = new BufferedWriter(new FileWriter(history));
		historyWriter.write("Chat-Session: " + Calendar.getInstance().getTime() + "\n");
			
      } catch (NullPointerException e) {
			e.printStackTrace();
	  } catch (IOException e) {
			e.printStackTrace();
	  } }

	
	public BufferedWriter getHistoryWriter() {
		return historyWriter;
	}
	
	public File getHistoryFile() {
		return history;
	}
      // inherited constructors


	

}

public class Client extends  Client$$History  {
	
	protected Message createMessage(String line) {
		Message msg = super.createMessage(line);
		
		if (line.indexOf("/ ") > 0) {
			return new RegisterMessage(line);
		} else if (line.indexOf("/msg ") > 0 ){
			return new PrivateMessage(line);
		} else {
			return msg;
		}
	}
      // inherited constructors



	//modified 15.05: gets Encrypter object that is being used for encryption during runtime 
	public Client ( String host, int port, Encrypter enc ) { super(host, port, enc); }

}