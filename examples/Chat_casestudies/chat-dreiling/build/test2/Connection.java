
package test2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;




/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
abstract class Connection$$Core implements Runnable {
	protected Socket socket;

	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;

	protected Server server;
	protected boolean connectionOpen = true;
	protected Thread thread;
	

	
	public Connection$$Core(Socket s, Server server) {
		this.socket = s;
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
			thread = new Thread(this);
			thread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.server = server;
	}

	public void stop() {
		thread.stop();
	}
	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		String clientName = socket.getInetAddress().toString();
		try {
			server.broadcast(new TextMessage(clientName + " has joined."));
			while (connectionOpen) {

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
			server.removeConnection(((Connection) this));
			server.broadcast(new TextMessage(clientName + " has left."));
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			String tmpContent = ((TextMessage) msg).getContent();			
			server.broadcast(((TextMessage) msg));
	
		}
	}


	public void send(TextMessage msg) {
		
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
			connectionOpen = false;
		}

	}
	
	

}



public class Connection extends  Connection$$Core  {
	/*if[AUTHO]*/	
	private boolean connectionAuthorized = false;
	private final static String AUTHORIZATIONMSG = "EPMD";
	/*end[AUTHO]*/
	
	
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			String tmpContent = ((TextMessage) msg).getContent();
			
			/*if[AUTHO]*/	
			if (!connectionAuthorized) {
				if (tmpContent.equals(AUTHORIZATIONMSG)) {
					connectionAuthorized = true;
					directSend("You are authorized now. Go ahead!");
				}
				else {
					directSend("Authorization failed. Try again with this here '"+AUTHORIZATIONMSG +"' :-)!");	
				}
				return;
			} 
			/*end[AUTHO]*/	
		}
			
		
		super.handleIncomingMessage(msg);
	
		
	}
	
	/*if[AUTHO]*/	
	/**
	 * sends a message directly to the client, without authorization
	 * 
	 * @param line
	 *            text of the message
	 */
	public void directSend(String line) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(new TextMessage(line));
			}
			outputStream.flush();
		} catch (IOException ex) {
			connectionOpen = false;
		}
	}
	/*end[AUTHO]*/	

	public void send(TextMessage msg) {
		/*if[AUTHO]*/	
		if (!connectionAuthorized)
			return;
		/*end[AUTHO]*/	
		
		
		super.send(msg);

	}
      // inherited constructors


	

	
	public Connection ( Socket s, Server server ) { super(s, server); }

}