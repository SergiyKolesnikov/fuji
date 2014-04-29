
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;




/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	
	protected Socket socket;
	boolean firstmsg = true;
	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Server server;
	protected boolean connectionOpen = true;

	public Connection(Socket s, Server server) {
		this.socket = s;
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
		} 
		catch (IOException e) { e.printStackTrace();}

		this.server = server;
	}

	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		String clientName = socket.getInetAddress().toString();
		try {
			server.broadcast(clientName , encrypt(" has joined."));
			while (connectionOpen) {

				try {
					Object msg = inputStream.readObject();
					
					//authen(msg, clientName, encrypt("Welcome :-)"), encrypt("Wrong password :-(") );
					if(firstmsg)authen(msg, clientName, encrypt("Welcome :-)"), encrypt("Wrong password :-(") );
					else handleIncomingMessage(clientName, msg);
				} 
				catch (ClassNotFoundException e) { e.printStackTrace();	}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			server.removeConnection(((Connection) this));
			try {
				server.broadcast(clientName , encrypt(" has left."));
			}  
			catch (IOException e) {}
			  
			try {
				socket.close();
			} catch (IOException ex) {	ex.printStackTrace(); }
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
	protected void handleIncomingMessage(String name, Object msg) throws IOException{
		if (msg instanceof TextMessage){
		
			server.broadcast(name , encrypt(" - " + ((TextMessage) msg).getContent()));}
			
	}

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String name, String line) {

		send(new TextMessage(name + decrypt(line)));
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
	
	//=========================================================
	public String encrypt (String args){return args;}
	public String decrypt (String args){return args;}
	public void authen (Object msg, String name, String passOk, String passWrong){
		
		try {
			handleIncomingMessage(name, msg);
		} 
		catch (Exception e) { System.out.println("error");	}
	
	}
}