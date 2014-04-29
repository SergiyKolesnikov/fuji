package client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import server.Server;

import common.*;

/**
 * simple chat client
 */
public class Client implements Runnable {
	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		new Client(args[0], Integer.parseInt(args[1]));
	}

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;
	
	protected String host;
	protected int port;
	protected User user;

	public Client(String host, int port) {
		try {
			this.host = host;
			this.port = port;
			
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
			this.user = getRandomUser();
			authenticate();
			
			while (thread == thisthread) {
				try {
					Object msg = inputStream.readObject();
					
					if (msg.toString().equals(Constants.LOGIN_FAILED)) 
					{
						return;
					} 
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
			TextMessage textMsg = (TextMessage) msg;
			String content = textMsg.getContent();
			String sender = textMsg.getSender();
			
			content = encodeMessage(sender, content);
			String line = sender + " - " + getColoredMessage(sender, content);
			fireAddLine(line);
			writeToFile(line);
		}
	}
	
	protected void authenticate() throws IOException
	{
		this.outputStream.writeObject(user.getUsername());
		this.outputStream.flush();
	}
	
	private void writeToFile(String line)
	{
	}
	
	protected String encodeMessage(String sender, String content)
	{
		return content;
	}
	
	protected String getColoredMessage(String sender, String content)
	{
		return content;
	}

	public void send(String line) {
		send(getMessage(line));
	}
	
	protected TextMessage getMessage(String line)
	{
		return new TextMessage(user.getUsername(), line);
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
	private ArrayList<ChatLineListener> listeners = new ArrayList<ChatLineListener>();

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
	 * fire Listener method for the observer pattern
	 */
	public void fireAddLine(String line) {
		for (Iterator<ChatLineListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = iterator.next();
			listener.newChatLine(line);
		}
	}

	public void stop() {
		thread = null;
	}
	
	private User getRandomUser()
	{
		String username = "user" + String.valueOf((new Random()).nextInt(4) +1);
		return Users.getUser(username); 
	}	
}
