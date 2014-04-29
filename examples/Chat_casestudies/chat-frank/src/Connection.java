
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;




/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
abstract class Connection$$Base extends Thread {
	
	protected Socket socket;
	boolean firstmsg = true;
	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Server server;
	protected boolean connectionOpen = true;

	public Connection$$Base(Socket s, Server server) {
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
	public void authen (Object msg, String name, String passOk, String passWrong){}
}

abstract class Connection$$Auth extends  Connection$$Base  {

	//boolean firstmsg = true;
	
	public void run() {
	
		super.run();
	}
	
	public void authen(Object msg, String clientName, String passOk, String passWrong){
							
			if(server.psswd.equals(((TextMessage)msg).getContentWithoutColor())) {
				send("", passOk); //keine Lust mehr...
				super.firstmsg=false;
			}
			else {send("", passWrong) ; }		
		
	}
      // inherited constructors



	public Connection$$Auth ( Socket s, Server server ) { super(s, server); }
	
}

public class Connection extends  Connection$$Auth  {
	
		//entschluesseln wenn client die Nachricht bekommt
		public void send(String name, String line) {
		
			super.send(name, line);			
		}
		
		//verschluesselt an den Server schicken
		public void run() {
			
			super.run();
		}
		
		protected void handleIncomingMessage(String name, Object msg) throws IOException{
		
			super.handleIncomingMessage(name, msg);
		}
		
		private String encryption (String arg){

        	return new StringBuffer(arg).reverse().toString();
    	}
    
		private String encryption2 (String arg){
		  	
		  	StringBuffer sb = new StringBuffer(arg);
		  	char a = sb.charAt(0); sb.deleteCharAt(0);
		  	char b = sb.charAt(sb.length()-1); sb.deleteCharAt(sb.length()-1);
		  	sb.append(a);
		  	sb.insert(0,b);
		  	return sb.toString();
		}

	
		public String encrypt (String args){
		
			return encryption(encryption2(args));
		}

		public String decrypt (String args){

			return encryption2(encryption(args));
		}
      // inherited constructors



	public Connection ( Socket s, Server server ) { super(s, server); }
}