package client; 

import java.io.EOFException; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.util.ArrayList; 
import java.util.Iterator; 
import common.TextMessage; 

import client.Color; 

import common.Encryption; 

import client.ChatLogger; 

/**
 * simple chat client
 */
public   class  Client  implements Runnable {
	

	public static void main(String args[]) throws IOException {
		/*
		 * Check commandline arguments
		 */
		if (args.length != 2) {
//			throw new RuntimeException("Syntax: ChatClient <host> <port>");
			new Client("localhost", 8080);
		} else {
			new Client(args[0], Integer.parseInt(args[1]));
		}
	}

	

	protected ObjectInputStream inputStream;

	
	protected ObjectOutputStream outputStream;

	
	protected Thread thread;

	

	public Client  (String host, int port) {
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
	
		new Gui(this);
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
	 private void  handleIncomingMessage__wrappee__Spamfilter  (Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage message = (TextMessage) msg;
			String sender = "";

			if (((TextMessage) msg).getSender() != null) {
				sender = message.getSender() + " - ";
			}

			fireAddLine(sender + message.getContent() + "\n");
		}
	}

	

	 private void  handleIncomingMessage__wrappee__PrivateMSG  (Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage message = (TextMessage) msg;
			colorPlugin.handleIncomingMessage(message);
			handleIncomingMessage__wrappee__Spamfilter(message);
		}
	}

	

	 private void  handleIncomingMessage__wrappee__Encryption  (Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage message = (TextMessage) msg;
			encrypt.handleIncomingMessage(message);
			handleIncomingMessage__wrappee__PrivateMSG(message);
		}
	}

	

	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			handleIncomingMessage__wrappee__Encryption(msg);
			logger.logMessage((TextMessage) msg);
		}
	}

	

	public void send(String line) {
		send(new TextMessage(line));
	}

	

	 private void  send__wrappee__Spamfilter  (TextMessage msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			this.stop();
		}
	}

	
	
	 private void  send__wrappee__PrivateMSG  (TextMessage msg) {
		colorPlugin.handleOutgoingMessage(msg);
		send__wrappee__Spamfilter(msg);
	}

	
	
	public void send(TextMessage msg) {
		encrypt.handleOutgoingMessage(msg);
		send__wrappee__PrivateMSG(msg);
	}

	

	/**
	 * listener-list for the observer pattern
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList listeners = new ArrayList();

	

	/**
	 * addListner method for the observer pattern
	 */
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("rawtypes")
	public void fireAddLine(String line) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(line);
		}
	}

	

	public void stop() {
		thread = null;
	}

	
	
	private Color colorPlugin = new Color();

	
	
	public Color getColorPlugin() {
		return this.colorPlugin;
	}

	
	
	private Encryption encrypt = new Encryption();

	
	
	public Encryption getEncrypt() {
		return this.encrypt;
	}

	
	ChatLogger logger = new ChatLogger();


}
