package client; 

import java.io.EOFException; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 

import common.TextMessage; 

public   class  Client  implements Runnable {
	

	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		new Client(args[0], Integer.parseInt(args[1]));
	}

	

	protected ObjectInputStream inputStream;

	
	protected ObjectOutputStream outputStream;

	
	protected Thread thread;

	
	protected boolean hasAccess = false;

	
	protected String user;

	
	protected int port;

	
	protected PluginManager manager;

	

	public Client(String host, int port) {
		this.user = host + String.valueOf((int) (Math.random() * 10000));
		this.port = port;
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
			manager = new PluginManager(this);
			Thread thisthread = Thread.currentThread();
			manager.onConnect();
			while (thread == thisthread) {
				Object msg = inputStream.readObject();
				
				if (msg instanceof TextMessage)
				manager.incomingMessage((TextMessage) msg);
			}
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			this.stop();
			try {
				outputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	

	 private void  send__wrappee__Color  (String line) {
		send(manager.send(line));
	}

	
	public void send(String line) {
		if(line.contains("/username")){
			user = line.substring(10);
		}else{ 
			if(line.contains("/msg")){
				line = line.substring(5);
				int separator = line.indexOf(" ");
				String reciever = line.substring(0, separator);
				String message = line.substring(separator + 1);
				send(manager.send(reciever, message));
				}else{
					send__wrappee__Color(line);
					}
		}
		
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

	

	public void stop() {
		manager.stop();
		thread = null;
	}

	
	
	protected void setAccess(boolean b){
		hasAccess = b;
	}

	
	
	public boolean hasAccess() {
		return hasAccess;
	}

	

	public String getUser() {
		return user;
	}

	

	public int getPort() {
		return port;
	}


}
