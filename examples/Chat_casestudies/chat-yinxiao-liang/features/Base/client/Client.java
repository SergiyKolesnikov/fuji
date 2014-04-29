package client;

import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import client.Gui;
import common.TextMessage;

/**
 * simple chat client
 */
public class Client implements Runnable {

	public static void main(String args[])throws IOException{
		
		boolean LoginFlag = false;
		
		Client client = new Client(LoginFlag);

		
	}
	
	public Socket s;
	public String host= "localhost";
	public int port= 8081;
	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;
	
	
	
	public Client(boolean LoginFlag) {
		
			connectToServer(!LoginFlag);
	}

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		try {
			Thread thisthread = Thread.currentThread();
			while (true) {
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
		}
	}

	public void send(String line) {
		send(new TextMessage(line));
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
	
synchronized boolean connectToServer(boolean LoginFlag){
	boolean Login = LoginFlag;
	if(Login==true)
	{
	try
	{
		System.out.println("Connecting to " + host + " (port " + port
				+ ")...");
		s = new Socket(host, port);
	}
	catch( UnknownHostException e )
	{
		JOptionPane.showMessageDialog( null,"Host Not Found, Reconfigure...","Host Lookup Error",JOptionPane.ERROR_MESSAGE );
		return false;
	}
	catch( IOException e )
	{
		JOptionPane.showMessageDialog( null,"Server Not Found, Check If Server Exists...","Socket Error",JOptionPane.ERROR_MESSAGE );
		System.exit(0);
		return false;
	}

	try
	{
		this.outputStream = new ObjectOutputStream((s.getOutputStream()));
		this.inputStream = new ObjectInputStream((s.getInputStream()));
	}
	catch( IOException e )
	{
		JOptionPane.showMessageDialog( null,"Cannot Create Data Stream, Closing Client...","Data Stream Error",JOptionPane.ERROR_MESSAGE );
		try
		{
			s.close();
		}
		catch( IOException io_e )
		{}
		
		return false;
	}
	new Gui("Chat " + "localhost" + ":" + "8081" , this);
	thread = new Thread(this);
	thread.start();
			
	return true;
	}
	else
		return false;
	}



}
