
package NewEquation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.awt.Color;



/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
abstract class Connection$$Chatbasic extends Thread {
	protected Socket socket;

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	public Server server;
	private boolean connectionOpen = true;

	public Connection$$Chatbasic(Socket s, Server server) {
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
		connectionAdd();
		String clientName = socket.getInetAddress().toString();
		try {
			TextMessage vmsg=join(clientName);
			server.broadcast(vmsg);

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
			TextMessage vmsg=left(clientName);
			server.removeConnection(((Connection) this));
			server.broadcast(vmsg);
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void connectionAdd(){
		server.connections.add(((Connection) this));
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage)

		{
			server.broadcast(new TextMessage((name + "-"),
							((TextMessage) msg)));

		}

		TextMessage nameMsg = new TextMessage((name + "-"), ((TextMessage) msg));

	}

	/**
	 * sends a message to the client
	 * 
	 * @param msg
	 *            object of the message
	 */

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
	
	public TextMessage join(String string){
		TextMessage msg=new TextMessage(string + " has joined.");
		return msg;
	}
	
	public TextMessage left(String string){
		TextMessage msg=new TextMessage(string + " has left.");
		return msg;
	}
	
	public void infoSend(Connection connection,TextMessage msg){
		
	}
	
	public void infoin(TextMessage msg){
		
	}
	
}



abstract class Connection$$Farbe extends  Connection$$Chatbasic  {

	public TextMessage join(String string){
	
		TextMessage msg=super.join(string);
		msg.farbe=Color.RED;
		return msg;
	}
	
	public TextMessage left(String string){
		
		TextMessage msg=super.left(string);
		msg.farbe=Color.RED;
		return msg;
	}
	
	public void infoSend(Connection connection,TextMessage msg){
		msg.farbe=Color.BLUE;		
		super.infoSend(connection,msg);
	}
      // inherited constructors



	public Connection$$Farbe ( Socket s, Server server ) { super(s, server); }
}



public class Connection extends  Connection$$Farbe  {

	public void infoSend(Connection connection,TextMessage msg){
		msg.verschieben();
		super.infoSend(connection,msg);
	}
	
	public void infoin(TextMessage msg){
		msg.entverschieben();
		super.infoin(msg);
		
	}
	
	public TextMessage join(String string){
		
		TextMessage msg=super.join(string);
		msg.verschieben();
		return msg;
	}
	
	public TextMessage left(String string){
		
		TextMessage msg=super.left(string);
		msg.verschieben();
		return msg;
	}
      // inherited constructors



	public Connection ( Socket s, Server server ) { super(s, server); }

}