package client;

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

import common.AuthMessage;
import common.Message;
import common.TextMessage;
import common.Encrypter;
import common.EncryptedMessage;
/**
 * simple chat client
 */
public class Client implements Runnable {
	
	//fields
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	protected Thread thread;
	protected static String username;
	
	private File history;
	private BufferedWriter historyWriter;
	private String currentColor = "black";
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
			Console console = new Console(client);
		}

	}

	//modified 15.05: gets Encrypter object that is being used for encryption during runtime 
	public Client(String host, int port, Encrypter enc) {
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
		
		try {
			//added 14.05: logfile for the Client
			new File("ClientLogs" + System.getProperty("file.separator")).mkdir();
			history = new File("ClientLogs" + System.getProperty("file.separator") + "history_" 
						+ System.currentTimeMillis() / 1000);
			historyWriter = new BufferedWriter(new FileWriter(history));
			historyWriter.write("Chat-Session: " + Calendar.getInstance().getTime() + "\n");
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
	
	//added 13.05.: login at server
	public void login(String password) {
		send(new AuthMessage(username, password));
	}

	//modified: 15.05.: handling of encrypted Messages
	public void send(String line) {
		
		Message msg = new TextMessage(line);
		
		if (enc != null) {
			EncryptedMessage encMsg = enc.encrypt(msg);
			msg = encMsg;
		}
			send(msg);
	}

	//modified 13.05: changed from TextMessage msg to Message msg
	public void send(Message msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			this.stop();
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
	
	//added: 13.05
	public BufferedWriter getHistoryWriter() {
		return historyWriter;
	}
	
	//added: 12.05
	public void setCurrentColor(String color) {
		this.currentColor = color;
	}
	
	//added: 12.05
	public String getCurrentColor() {
		return this.currentColor;
	}
	
	//added: 13.05
	public File getHistoryFile() {
		return history;
	}
	
}
