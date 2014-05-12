
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



//import TextMessage;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
abstract class Connection$$Base extends Thread {
	protected Socket socket;

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Server server;
	protected boolean connectionOpen = true;
	protected boolean pass = true;

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
	 public void authInc(String clientName, Object msg) {
	 	handleIncomingMessage(clientName, msg);
	 }
	 public String decrypt1(String txt) {
	 	return txt;
	 }
	 public String encrypt2(String txt) {
	 	return txt;
	 }
	public void run() {
		String clientName = socket.getInetAddress().toString();
		try {
			server.broadcast(clientName + " has joined.");
			while (connectionOpen) {

				try {
					Object msg = inputStream.readObject();
					authInc(clientName,msg);
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
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage)
			server.broadcast(name + " - " + decrypt1(((TextMessage) msg).getContent()));
	}

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String line) {
		send(new TextMessage(encrypt2(line)));
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

abstract class Connection$$Encryption extends  Connection$$Base  {
	public String decrypt1(String line) {
		String line2 = "";
		for(int i=line.length()-1;i>=0;i--)
			line2 = line2 + String.valueOf(line.charAt(i));
		return line2;
	}
	
	public String encrypt2(String line) {
	    return String.valueOf(line.charAt(1)) + String.valueOf(line.charAt(0)) + line.substring(2);
	}
      // inherited constructors



	public Connection$$Encryption ( Socket s, Server server ) { super(s, server); }
}

public class Connection extends  Connection$$Encryption  {
	public void authInc(String clientName, Object msg) {
		if(msg instanceof TextMessage && super.pass) {
			if(((TextMessage)msg).getContent().equals(super.decrypt1("passwort junge"))) {
				super.server.broadcast(clientName + " login successful.");
				super.pass = false;
			}
			else {
				super.server.broadcast(clientName + " wrong password.");
				super.connectionOpen = false;
				super.pass = false;
			}
		}
		else {
			super.authInc(clientName,msg);
		}
	}
      // inherited constructors



	public Connection ( Socket s, Server server ) { super(s, server); }
}