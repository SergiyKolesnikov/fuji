package client; 

//#if logging
//@import java.io.BufferedWriter;
//@import java.io.FileWriter;
//#endif
import java.io.IOException; 

import java.io.EOFException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 

import java.util.ArrayList; 
import java.util.Iterator; 

import common.TextMessage; 

import common.AuthenticationMessage; 
import common.ServerCommandMessage; 

/**
 * TODO Catch here all Messages and test on ServerCommand beeing.
 */
public   class  Client  implements Runnable {
	
	public Gui gui;

	
	public String username = "unnamed";

	
	
	public static void main(String args[]) throws IOException {
		if (args.length < 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
		
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		
		Client client = new Client(host, port, args);
		
		new Gui("Chat " + args[0] + ":" + args[1], client);
	}

	

	protected ObjectInputStream inputStream;

	

	protected ObjectOutputStream outputStream;

	

	protected Thread thread;

	

	public Client(String host, int port, String[] args) {
		
		initStuff(host, port, args);
		
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

	
	 private void  initStuff__wrappee__Logging  (String host, int port, String[] args){}

	
	
	private void initStuff(String host, int port, String[] args){
		if(args.length < 4){
			System.out.println("Error: You forgot the Username and Password! \n \t Syntax: ChatClient <host> <port> (<username> <password>)");
			System.exit(1);
		}else{
			this.username = args[2];
			this.password = args[3];
		}
		initStuff__wrappee__Logging(host, port, args);
	}

	

	/**
	 * main method. waits for incoming messages.
	 */
	 private void  run__wrappee__Logging  () {
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

	
	
	public void run() {	
		this.send(new AuthenticationMessage(username, password));
		run__wrappee__Logging();
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

	

	 private void  send__wrappee__MD5  (String line) {
		send(new TextMessage(line));
	}

	
	public void send(String line) {
		ServerCommandMessage commandMsg = ServerCommandMessage.tryInterpretAsServerCommand(line);
		if(commandMsg != null){
			send(commandMsg);
		}else{
			send__wrappee__MD5(line);
		}
	}

	

	public void send(Object msg) {
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

	
	private boolean amLoadingAnLogFile;

	
	private String password;


}
