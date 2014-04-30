
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;






/**
 * simple chat client
 */
abstract class Client$$Base implements Runnable {
	
	
    public static Integer connectionFinished = new Integer(0);
	
	public String name = "default";
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	protected Thread thread;
		
	public Client$$Base(String host, int port) {
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
		init();
	}

	protected void init(){
		
	
	}

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		try {
			while (true) {
				try {
					Object msg = inputStream.readObject();
					handleIncomingMessage(msg);
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
		if (msg instanceof String) {
			fireAddLine(msg+"\n");
		}	
		if (msg instanceof Message) {
			fireAddLine(((Message) msg));
		}
			
	}

	public void send(String line) {
		
		Message mess = new Message();
		mess.addComponent("name", name);
		mess.addComponent("content", line);
		sendMess(mess);
	}

	public void sendMess(Message msg) {
		
		
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			thread.stop();
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
	
	public void fireAddLine(Message line) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(line);
		}
	}
	

	public void stop() {
		System.exit(0);
		thread.stop();
	}

}




abstract class Client$$Console extends  Client$$Base  {
	
	Console myConsole;
	
	protected void handleIncomingMessage(Object msg) {
		super.handleIncomingMessage(msg);
		if(msg instanceof Message){
		//	myConsole.newChatLine((Message)msg);
		}	
	}
      // inherited constructors


		
	public Client$$Console ( String host, int port ) { super(host, port); }
}



abstract class Client$$Crypt extends  Client$$Console  {

	Decryption crypt;

	protected void handleIncomingMessage(Object msg) {
		if(msg instanceof Message){
			Message local = (Message) msg;
			if(local.hasComponent("content")){
				String content = (String) local.getComponent("content");
				String newCont = crypt.decrypt(content);
				local.overrideComponent("content", newCont);
			}
		}
		super.handleIncomingMessage(msg);
		
	}

	public void sendMess(Message msg) {
		if(msg.hasComponent("content")){
			String content = (String) msg.getComponent("content");
			String newCont = crypt.enctypt(content);
			msg.overrideComponent("content", newCont);
		}
		super.sendMess( msg);
	}
      // inherited constructors


		
	public Client$$Crypt ( String host, int port ) { super(host, port); }
}



abstract class Client$$POT13 extends  Client$$Crypt  {

	protected void init(){
		super.init();	
	
		crypt = new POT13();
	}
      // inherited constructors


		
	public Client$$POT13 ( String host, int port ) { super(host, port); }
	
}



public class Client extends  Client$$POT13  {

	Log logFile;

	protected void handleIncomingMessage(Object msg) {
	
		if(msg instanceof Message){
			if(logFile == null){
				logFile = new Log("Client"+((Client) this).name+Math.random());
			}
			logFile.log((Message)msg);
		}
	
		super.handleIncomingMessage(msg);	
	}
      // inherited constructors


		
	public Client ( String host, int port ) { super(host, port); }

}