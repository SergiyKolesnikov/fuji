package server; 

import java.io.EOFException; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.net.SocketException; 
import common.TextMessage; 

import server.SpamfilterPlugin; 

import common.Encryption; 

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public   class  Connection  extends Thread {
	
	protected Socket socket;

	
	protected ObjectInputStream inputStream;

	
	protected ObjectOutputStream outputStream;

	
	private Server server  ;

	

	public Connection(Socket s, Server server) {

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
	 * waits for incoming messages from the socket also waits for authentication
	 */
	public void run() {
		String clientName = socket.getInetAddress().toString();
		registerConnection(null);
		try {
			Object msg = null;
			while ((msg = inputStream.readObject()) != null) {
				handleIncomingMessage(clientName, msg);
			}
		} catch (SocketException e) {
		} catch (EOFException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.removeConnection(this);
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
	 private void  handleIncomingMessage__wrappee__Authentication  (String name, Object msg) {
		if (msg instanceof TextMessage) {
			if (isAuthenticated) {
				((TextMessage) msg).setSender(name);

				server.broadcast(((TextMessage) msg));
			} else {
				registerConnection((TextMessage) msg);
			}
		}
	}

	

	 private void  handleIncomingMessage__wrappee__Color  (String name, Object msg) {
		if (msg instanceof TextMessage) {
			spamfilter.handleIncomingMessage((TextMessage)msg);
			handleIncomingMessage__wrappee__Authentication(name,msg);
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
	 private void  handleIncomingMessage__wrappee__Name  (String name, Object msg) {
		if (msg instanceof TextMessage) {
			String content = ((TextMessage) msg).getContent();
			if (content.startsWith("/username")) {
				this.myName = content.substring(content.indexOf(" ") + 1);
				this.send("Name in " + this.myName + " ge�ndert");
			} else {
				if (this.myName != null) {
					handleIncomingMessage__wrappee__Color(this.myName, msg);
				} else {
					handleIncomingMessage__wrappee__Color(name, msg);
				}
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
	 private void  handleIncomingMessage__wrappee__PrivateMSG  (String name, Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage message = (TextMessage) msg;
			String content = message.getContent();
			if (content.startsWith("/msg")) {
				String[] split = content.split(" ", 3);

				message.setEmpfaenger(split[1]);
				message.setContent(split[2]);
			}

			handleIncomingMessage__wrappee__Name(name, message);

		}
	}

	

	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage) {
			encrypt.handleIncomingMessage((TextMessage)msg);
			handleIncomingMessage__wrappee__PrivateMSG(name,msg);
		}
	}

	

	/**
	 * as long as this connection is not authenticated, all input will test as
	 * password
	 * 
	 * @param msg
	 */
	private void registerConnection  (TextMessage msg) {
		if (!isAuthenticated) {
			this.setIsAuthenticated(this.server.authenticate(msg));
		}
		
		if (!isAuthenticated) {
			send("Bitte geben Sie ein Passwort ein zum einloggen:");
		} else {
			send("Sie sind eingeloggt!");
			server.loginClient(this);
		}
	}

	

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	 private void  send__wrappee__Color  (String line) {
		send(new TextMessage(line));
	}

	
	public void send(String line) {
		send__wrappee__Color(line);
	}

	

	 private void  send__wrappee__PrivateMSG  (TextMessage msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
		}
	}

	
	
	public void send(TextMessage msg) {
		encrypt.handleOutgoingServerMessage(msg);
		send__wrappee__PrivateMSG(msg);
		
	}

	
	private boolean isAuthenticated = false;

	
	
	public void setIsAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	
	private SpamfilterPlugin spamfilter = new SpamfilterPlugin();

	

	private String myName  ;

	

	public String getMyName() {
		return this.myName;

	}

	
	private Encryption encrypt = new Encryption();


}
