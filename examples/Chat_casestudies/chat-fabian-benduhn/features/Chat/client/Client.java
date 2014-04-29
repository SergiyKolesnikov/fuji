package client;

import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.text.BadLocationException;


import common.TextMessage;



/**
 * simple chat client
 */
public class Client implements Runnable {


	public static void main(String args[]) throws IOException, BadLocationException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
			Client client = new Client(args[0], Integer.parseInt(args[1]));
	

	}



	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;
	private void ClientConstructor(String host, int port){
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
	

	public Client(String host, int port) {
		ClientConstructor(host,port);
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
				} catch (BadLocationException e) {
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
	 * @throws BadLocationException 
	 * @throws IOException 
	 */
	 void handleIncomingMessage(Object msg) throws BadLocationException, IOException {
	String line=((TextMessage) msg).getContent();
		if (msg instanceof TextMessage) {

			try{
			handleLine(line,false,msg);
			}
			catch( Exception e){
				
			}
		}
	}
	 
	 private void handleLine(String line, boolean encrypted,Object msg)throws Exception{
	
		
			
			fireAddLine(line);
	 }
	 
	 

	public void send(String line) {

		send(new TextMessage(line,false));
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
	 * @throws BadLocationException 
	 */
	@SuppressWarnings("rawtypes")
	public void fireAddLine(String line) throws BadLocationException {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(line);
		}
	}

	public void stop() {
		thread = null;
	}
}
