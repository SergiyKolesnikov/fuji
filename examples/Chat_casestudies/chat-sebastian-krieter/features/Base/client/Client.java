package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

import common.Commands;
import common.TextMessage;

public class Client implements Runnable {

	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
		
		INSTANCE = new Client(args[0], Integer.parseInt(args[1]));
	}
	
	private static Client INSTANCE;

	protected ObjectInputStream inputStream = null;
	protected ObjectOutputStream outputStream = null;
	protected Thread thread;
	
	private static String username = null;

	private Client(String host, int port) {
		Socket s = null;
		try {
			Socket ts = new Socket(host, port);			
			outputStream = new ObjectOutputStream((ts.getOutputStream()));
			inputStream = new ObjectInputStream((ts.getInputStream()));			
			s = ts;			
		} catch (ConnectException e) {
			newChatLine(Message.info("No server found."));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (s != null) {
			String loginname = login();
			if (loginname != null) {
				setUsername(loginname);
				thread = new Thread(this);
				thread.start();
			} else {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		Thread thisthread = Thread.currentThread();
		while (thread == thisthread) {
			try {
				handleIncomingMessage(inputStream.readObject());
			} catch (Exception e) {
				break;
			}
		}
		newChatLine(Message.info("Connection lost."));
		stop();
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg != null && msg instanceof TextMessage) {
			Message m = new Message(username, (TextMessage) msg);
			
			incomingAction(m);
			newChatLine(m);
		}
	}
	
	private static void stop() {
		INSTANCE.thread = null;
	}
	
	public static boolean canSend() {
		return INSTANCE.thread != null && INSTANCE.outputStream != null;
	}
	
	private static void sendObject(Object msg) {
		try {
			INSTANCE.outputStream.writeObject(msg);
			INSTANCE.outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void sendMessage(String line) {
		if (canSend()) {
			sendObject(toTextMessage(line));
		}
	}
	
	private static TextMessage toTextMessage(String line) {
		return new TextMessage(username, line);		
	}
	
	public static void close() {
		if (canSend()) {
			sendObject(null);
		}
		stop();		
        System.exit(0);
	}
	
	private void setUsername(String name) {
		username = name;
	}
	
	private void incomingAction(Message msg) {
		
	}
	
	private void newChatLine(Message msg) {
		
	}

	private String login() {
		try {
			outputStream.writeInt(Commands.COMMAND_LOGIN);
			outputStream.flush();
			
			if (Commands.RESPONSE_OK == inputStream.readInt()) {
				return inputStream.readObject().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
