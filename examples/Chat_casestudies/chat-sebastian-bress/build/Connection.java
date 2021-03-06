


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



//import common.TextMessage;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
abstract class Connection$$Base extends Thread {
	
	protected Socket socket;

	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;

	protected Server server;
	private boolean connectionOpen = true;

	//needed for writing the log file
	protected File file;
    protected BufferedWriter bw;
    
    //you may want to change the location of the log file
    private String logfile_path = "/home/sebastian/Programme/chat_client_messages.log";
	
	
	public Connection$$Base(Socket s, Server server) {
		this.socket = s;
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.server = server;
	}

	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		String clientName = socket.getInetAddress().toString();
		try {
			server.broadcast(clientName + " has joined.");
			while (connectionOpen) {

				try {
					Object msg = inputStream.readObject();
					handleIncomingMessage(clientName, msg);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			server.removeConnection(((Connection) this));
			server.broadcast(clientName + " has left.");
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	
	
	
	/**
	 * the Client has to authenticate himself by the Server. First the Client sends a request 
	 * with the password to the Server. Then he waits for a reply. If the passwort fits, the client is successfully 
	 * authenticated and he gets the status ok, otherwise a 'failed' is returned.   
	 */
	
		public boolean initialize_Connection() {
			
			return true;
		}
	
	/*
	public boolean initialize_Connection() {
		
		
		Object msg= new Object(); //to avoid the "not initialized error"
		
		try {
			msg = inputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		if (msg instanceof TextMessage) {
			TextMessage tms = (TextMessage) msg;
			
			if(tms.getMessagetyp().equals("authenticate")){
			
				if(tms.getPassword().equals(server.password)){
					
					send(new TextMessage("authenticated","","","","ok"));
					return true; //accept connection
					
				}else{		
					
					System.err.println("invalid passwort! Connection refused...");
					send(new TextMessage("authenticated","","","","failed"));
					
				}
				
			}
			
		}
		
		return false; //something went wrong, refuse connection
			
	}
	*/
	

	
	
	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	protected void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage)
			
			//log incoming Message 
			//log(((TextMessage) msg).getContent());
            //server.log(((TextMessage) msg).getContent());
			
			if(((TextMessage) msg).getMessagetyp().equals("send")){
				
				server.broadcast((TextMessage) msg);
				
			}
			//server.broadcast(name + " - " + ((TextMessage) msg).getContent());
	}

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String line) {
		send(new TextMessage(line));
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



abstract class Connection$$Authentification extends  Connection$$Base  {


	/**
	 * the Client has to authenticate himself by the Server. First the Client sends a request 
	 * with the password to the Server. Then he waits for a reply. If the passwort fits, the client is successfully 
	 * authenticated and he gets the status ok, otherwise a 'failed' is returned.   
	 */
	
	public boolean initialize_Connection() {
		
		
		Object msg= new Object(); //to avoid the "not initialized error"
		
		try {
			msg = inputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		if (msg instanceof TextMessage) {
			TextMessage tms = (TextMessage) msg;
			
			if(tms.getMessagetyp().equals("authenticate")){
			
				if(tms.getPassword().equals(server.password)){
					
					send(new TextMessage("authenticated","","","","ok"));
					return true; //accept connection
					
				}else{		
					
					System.err.println("invalid passwort! Connection refused...");
					send(new TextMessage("authenticated","","","","failed"));
					
				}
				
			}
			
		}
		
		return false; //something went wrong, refuse connection
			
	}
      // inherited constructors


	
	
	public Connection$$Authentification ( Socket s, Server server ) { super(s, server); }

}



public class Connection extends  Connection$$Authentification  {

     protected void handleIncomingMessage(String name, Object msg) {
		
			
			//log incoming Message 
			//log(((TextMessage) msg).getContent());
            server.log((TextMessage) msg);
			super.handleIncomingMessage(name, msg);
     }
      // inherited constructors


	
	
	public Connection ( Socket s, Server server ) { super(s, server); }		


}