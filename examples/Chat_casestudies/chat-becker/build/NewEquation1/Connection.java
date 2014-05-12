
package NewEquation1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;




abstract class Connection$$Basic extends Thread{
	protected Socket socket;
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	private Server server;
	private boolean connectionOpen = true;

	public Connection$$Basic(Socket s, Server server) {
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
			server.broadcast(new TextMessage(clientName+ " has joined."));
			while (connectionOpen) {

				try {
					Object msg = inputStream.readObject();
					if(msg instanceof TextMessage)
						handleIncomingMessage(clientName,(TextMessage) msg);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			server.removeConnection(((Connection) this));
			server.broadcast(new TextMessage(clientName+ "has left."));
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
	public void handleIncomingMessage(String name, TextMessage msg) {
			msg.setContent(name+ " - "+ msg.getContent());
			server.broadcast(msg);
		
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



abstract class Connection$$Crypt extends  Connection$$Basic  {
	//Eingehende Nachrichten werden entschlüsselt
	public void handleIncomingMessage(String name, TextMessage msg){
		msg.setContent( fliptext( msg.getContent()) );
		msg.setContent( doConvert( msg.getContent()) );
		super.handleIncomingMessage(name,msg);	
		
	}
	//Ausgehende Nachrichten werden entschlüsselt
	public void send(TextMessage msg){
		msg.setContent( fliptext( msg.getContent()) );
		msg.setContent( doConvert( msg.getContent()) );
		super.send(msg);
	
	}
	
	//String wird vertauscht
	private String fliptext (String text){
		text=new StringBuffer(text).reverse().toString();
		return text;
		
	}
	//ROT 13 
	//Quelle: http://en.literateprograms.org/Rot13_(Java)
	 public String doConvert(String in) {
		String encodedMessage = "";
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if      (c >= 'a' && c <= 'm') c += 13;
			else if (c >= 'n' && c <= 'z') c -= 13;
			else if (c >= 'A' && c <= 'M') c += 13;
			else if (c >= 'N' && c <= 'Z') c -= 13;
			encodedMessage += c;
		}
		return encodedMessage;
       
    
	}
      // inherited constructors



	public Connection$$Crypt ( Socket s, Server server ) { super(s, server); }
	
	
}



public class Connection extends  Connection$$Crypt  {
	private String password="EPMD4EVER";
	Boolean auth=false;
	public void handleIncomingMessage(String name, TextMessage msg){
		if(msg instanceof AuthMessage){
			if(msg.getContent().equals(password))
				auth=true;
		}
		
		if(auth)
			super.handleIncomingMessage(name,msg);
		else
			send(new TextMessage("Keine Erfolgreiche Authentification"));	
	}
      // inherited constructors



	public Connection ( Socket s, Server server ) { super(s, server); }
}