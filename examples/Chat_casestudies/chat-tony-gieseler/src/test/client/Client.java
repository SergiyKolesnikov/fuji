package client; 

import java.io.BufferedWriter; 
import java.io.FileWriter; 

import java.io.IOException; 
import java.io.EOFException; 

import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.util.ArrayList; 
import java.util.Iterator; 

import common.TextMessage; 

import common.Change01; 

/**
 * TODO description
 */
public   class  Client  implements Runnable {
	
	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		Client client = new Client(args[0], Integer.parseInt(args[1]));
		new Gui("Chat " + args[0] + ":" + args[1], client);
	}

	

	protected ObjectInputStream inputStream;

	
	protected ObjectOutputStream outputStream;

	
	protected Thread thread;

	
	

	public Client(String host, int port) {
		start(host, port);
	}

	
	
	 private void  start__wrappee__Authentifizierung  (String host, int port) {
		try {
			Socket s = new Socket(host, port);
			this.outputStream = new ObjectOutputStream((s.getOutputStream()));
			this.inputStream = new ObjectInputStream((s.getInputStream()));
			
			this.outputStream.writeBytes(pass);
			this.outputStream.flush();
			
			thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void start(String host, int port) {
		Change01 v = new Change01();
		pass = v.crypt(pass);
		
		start__wrappee__Authentifizierung(host, port);
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
	 * @throws IOException 
	 */
	 private void  handleIncomingMessage__wrappee__Basis  (Object msg) throws IOException {
	}

	
	 private void  handleIncomingMessage__wrappee__Username  (Object msg) throws IOException {
		handleIncomingMessage__wrappee__Basis(msg);
		if (msg instanceof TextMessage)	
			fireAddLine(((TextMessage) msg).getContent() + "\n");
	}

	
	protected void handleIncomingMessage(Object msg) throws IOException {
		if (msg instanceof TextMessage) {
			String str = ((TextMessage) msg).getContent();
			if(str.regionMatches(false, username.length()+16, "/msg", 0, 4)) {
				if(str.regionMatches(false, username.length()+21, username, 0, username.length()))
					handleIncomingMessage__wrappee__Username(new TextMessage(str.substring(0, username.length()+14) + ": " + str.substring(2*username.length()+21)));
			} else {
				handleIncomingMessage__wrappee__Username(msg);
				
			}
		}
	}

	

	 private void  send__wrappee__Change01  (String line) {
		send(new TextMessage(line));
	}

	
	
	public void send(String line) {
		if(line.regionMatches(false, 0 ,"/username", 0, 9)) 
				username = line.substring(10, line.length());
		send__wrappee__Change01(" " + username + ": " + line);
	}

	

	public void send(TextMessage msg) {
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

	
	private String pass = "Passwort";

	
	private String username = "nickname";


}
