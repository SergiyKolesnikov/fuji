
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;



abstract class Connection$$Base extends Thread {
	protected Socket socket;
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;
	protected Server server;
	protected Encrypter enc;

	public Connection$$Base(Socket s, Server server, Encrypter enc) {
		this.socket = s;
		this.enc = enc;
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
			Object msg = null;
			while ((msg = inputStream.readObject()) != null) {
				handleIncomingMessage(clientName, msg);
			}
			server.broadcast(clientName + " has joined.");
		} catch (SocketException e) {
		} catch (EOFException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	protected void handleIncomingMessage(String name, Object msg) {
		
		String line;
		if (msg instanceof TextMessage) {
			line = ((TextMessage) msg).getContent();
			server.broadcast(line);
		}
		//what to do with Encrypted Messages
		else if (msg instanceof EncryptedMessage) {
			line = enc.decrypt((EncryptedMessage) msg);
			server.broadcast(line);
		}
	}

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(String line) {
		Message msg = new TextMessage(line);
		if (enc != null) {
			EncryptedMessage encMsg = enc.encrypt(msg);
			msg = encMsg;
		}
		send(msg);
	}
	
	//modified 15.05: TextMessage to Message msg
	public void send(Message msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
		}
	}
}

abstract class Connection$$Authentification extends  Connection$$Base  {
	
	protected void handleIncomingMessage(String name, Object msg) {
		super.handleIncomingMessage(name, msg);
	
		if (msg instanceof AuthMessage) {
			server.checkLogin(((Connection) this), ((AuthMessage) msg).getPassword());
		}
	}
      // inherited constructors



	public Connection$$Authentification ( Socket s, Server server, Encrypter enc ) { super(s, server, enc); }
}

abstract class Connection$$History extends  Connection$$Authentification  {
	
	protected void handleIncomingMessage(String name, Object msg) {
      super.handleIncomingMessage(name, msg);
      
      String line;
		if (msg instanceof TextMessage) {
			line = ((TextMessage) msg).getContent();
			server.addToHistory(line);
		}
		//what to do with Encrypted Messages
		else if (msg instanceof EncryptedMessage) {
			line = enc.decrypt((EncryptedMessage) msg);
			server.addToHistory(line);
		}
		
	}
      // inherited constructors



	public Connection$$History ( Socket s, Server server, Encrypter enc ) { super(s, server, enc); }

}

public class Connection extends  Connection$$History  {
	
	protected void handleIncomingMessage(String name, Object msg) {
		super.handleIncomingMessage(name, msg);
		
		//hier Nachricht auf Nutzernamen prÃÂ¼fen
	    //eventuell nutzer in hashmap registrieren
	    //weiterleiten an bestimmten nutzer
		if (msg instanceof PrivateMessage) {
			String text = ((PrivateMessage) msg).getMessage();
			server.sendToUser(getUsernameFromMessage(text),text);
		}
		
		if (msg instanceof RegisterMessage) {
			String username = ((RegisterMessage) msg).getUsername();
			server.registerUser(getUsernameFromMessage(username), Connection.this);
		}
	
	}
	
	 private String getUsernameFromMessage(String text) {
	 	int index = text.indexOf("/");
		text = text.substring(index);
		String[] tokens = text.split("\\s");
	 	return tokens[1];
	 }
      // inherited constructors



	public Connection ( Socket s, Server server, Encrypter enc ) { super(s, server, enc); }

}