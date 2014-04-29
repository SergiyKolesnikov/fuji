package client; 

import java.io.IOException; 
import java.io.EOFException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.util.ArrayList; 
import java.util.Iterator; 

import common.*; 

public   class  Client  implements Runnable {
	
	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		Client client = new Client(args[0], Integer.parseInt(args[1]));
		
		// Feature: UI
		feat_UI_init(args, client);

		if (!client.isConnected()) {
			client.fireAddLine("access denied");
		}
	}

	

	private boolean _isConnected;

	
	
	protected ObjectInputStream inputStream;

	

	protected ObjectOutputStream outputStream;

	

	protected Thread thread;

	
	
	protected String clientName;

	

	public Client(String host, int port) {
		try {
			System.out.println("Connecting to " + host + " (port " + port
					+ ")...");
			Socket s = new Socket(host, port);
			this.clientName = s.getLocalAddress().toString();
			this.outputStream = new ObjectOutputStream((s.getOutputStream()));
			this.inputStream = new ObjectInputStream((s.getInputStream()));
			
			//#if Authentification
			// send password
			this.outputStream.writeObject("UnH4cK4b3Lp455W0rD"); // correct password
			//this.outputStream.writeObject("password"); // incorrect password
			this.outputStream.flush();
			//#endif
			
			// server sends true if connection was accepted
			isConnected(inputStream.readBoolean());
			
			if (isConnected()) {
				thread = new Thread(this);
				thread.start();				
			}
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
		if (msg instanceof ITextMessage) {
			// Feature: Encryption
			fireAddLine(feat_Encryption_getMessage(msg) + "\n");
		}
	}

	

	private String feat_Encryption_getMessage(Object msg) {
		return ((ITextMessage) msg).toString();
	}

	
	public void send(String line) {
		send(new TextMessage(line, clientName));
	}

	

	public void send(ITextMessage msg) {
		if (isConnected()) {
			try {
				outputStream.writeObject(msg);
				outputStream.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
				this.stop();
			}
		}
	}

	

	/**
	 * listener-list for the observer pattern
	 */
	private ArrayList<IUserInterface> listeners = new ArrayList<IUserInterface>();

	

	/**
	 * addListner method for the observer pattern
	 */
	public void addLineListener(IUserInterface listener) {
		listeners.add(listener);
	}

	

	/**
	 * removeListner method for the observer pattern
	 */
	public void removeLineListener(IUserInterface listner) {
		listeners.remove(listner);
	}

	

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(String line) {
		for (Iterator<IUserInterface> iterator = listeners.iterator(); iterator.hasNext();) {
			IUserInterface listener = (IUserInterface) iterator.next();
			listener.messageReceived(line);
		}
	}

	

	public void stop() {
		thread = null;
	}

	
	
	public boolean isConnected() {
		return _isConnected;
	}

	
	private void isConnected(boolean value) {
		_isConnected = value;
	}

	

	public String getName(){
		return clientName;
	}

	
	
	public void setName(String name){
		clientName = name;
	}

	

	private static void feat_UI_init  (String[] args, Client client) {
		// Feature: Gui
		feat_Gui_startGui("Chat " + args[0] + ":" + args[1], client);		
		
		// Feature: Console
		feat_Console_startConsole(client);
	}

	;

	;

	
	
	private static void feat_Gui_startGui(String title, Client client) {}

	
	private static void feat_Console_startConsole  (Client client) {
		new Console(client);
	}


}
