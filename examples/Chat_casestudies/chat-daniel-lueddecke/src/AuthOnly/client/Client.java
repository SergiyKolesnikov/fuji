package client; 

import interfaces.IClient; 
import interfaces.IClientProxy; 

import java.io.IOException; 
import java.io.EOFException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.util.ArrayList; 
import java.util.List; 

import common.TextMessage; 

import org.apache.log4j.FileAppender; 
import org.apache.log4j.Level; 
import org.apache.log4j.Logger; 
import org.apache.log4j.PatternLayout; 
import org.apache.log4j.RollingFileAppender; 

import client.Client; 
import clientPlugIns.ConsoleClient; 

/**
 * simple chat client
 */
public   class  Client  implements Runnable, IClientProxy {
	
	
	 private static void  main__wrappee__Client  (String args[]) throws IOException {
		
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
	}

	
	
	 private static void  main__wrappee__Logging  (String args[]) throws IOException {
		
		logger.setLevel(Level.ALL);
		
		if(canLogFile != null)
			logger.addAppender(getAppender(canLogFile));
		
		main__wrappee__Client(args);
	}

	
	
	public static void main(String args[]) throws IOException {
		
		main__wrappee__Logging(args);
		
		Client client = new Client(args[0], Integer.parseInt(args[1]));
		//add the Console Client
		client.addClient(new ConsoleClient());
	}

	
	
	protected ObjectInputStream inputStream;

	
	protected ObjectOutputStream outputStream;

	
	protected Thread thread;

	
	
	private List<IClient> clients;

	

	public Client(String host, int port) {
		
		prepareStart();
		
		clients = new ArrayList<IClient>();
		
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

	
	
	private void prepareStart() {
		return;
	}

	
	
	@Override
	public boolean addClient(IClient client) {
		client.setClientProxy(this);
		client.start();
		return clients.add(client);
	}

	
	
	@Override
	public boolean removeClient(IClient client) {
		client.setClientProxy(null);
		client.stop();
		return clients.remove(client);
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
			TextMessage tm = (TextMessage) msg;
			fireAddLine(tm.getSource() + " - " + tm.getContent()+ "\n");
		}
	}

	

	public void send(String line) {
		send(new TextMessage(line, null));
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
	 * fire Listner method for the observer pattern
	 */
	 private void  fireAddLine__wrappee__Client  (String line) {		
		for (IClient client : clients) {
			client.newChatLine(line);
		}
	}

	

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(String line) {
		logger.info(line);
		
		fireAddLine__wrappee__Client(line);
	}

	

	public void stop() {
		thread = null;
	}

	
	private static Logger logger = Logger.getLogger(Client.class);

	 
	private final static String canLogFile = "log/log.log";

	
	
	private static FileAppender getAppender(String fileName) {  
        RollingFileAppender appender = null;  
        try {  
            appender = new RollingFileAppender(  
                    new PatternLayout("%d{MM/dd/yyy - HH:mm:ss}: %m%n"), fileName, true);
            appender.setMaxBackupIndex(5);  
            appender.setMaxFileSize("1MB");  
        } catch (IOException ex) {  
            System.out.println("Could not create appender for " + fileName + ":" + ex.getMessage());  
        }  
        return appender;  
    }


}
