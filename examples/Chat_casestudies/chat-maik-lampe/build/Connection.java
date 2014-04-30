

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

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Server server;
	private boolean connectionOpen = true;

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
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	protected void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage)
			server.broadcast(name + " - " + ((TextMessage) msg).getContent());
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



abstract class Connection$$Krypto extends  Connection$$Base  {	
	
	protected void handleIncomingMessage(String name, Object msg) {
		String message = ((TextMessage) msg).getContent();
		TextMessage newmsg = new TextMessage(krypto.rot(message));
		super.handleIncomingMessage(name, (Object) newmsg);
	}
      // inherited constructors



	public Connection$$Krypto ( Socket s, Server server ) { super(s, server); }
}



public class Connection extends  Connection$$Krypto  {	
	 
 protected String password = "default";
 
 protected boolean connectionOpen = false;
	
 public void run() {
        String clientName = socket.getInetAddress().toString();
        try {
            String firstMessage = "empty";
				
            try {
                Object msg = inputStream.readObject();
                firstMessage = krypto.rot(((TextMessage) msg).getContent());
				if(firstMessage.contains("passwd:" + ((Connection) this).password)) {
					((Connection) this).connectionOpen = true;
				}
            } catch (ClassNotFoundException ex) {
                ((Connection) this).connectionOpen = false;
            }
            
            if(connectionOpen) {
                String message = clientName + " has joint. Welcome.";
                server.broadcast(message);
            } else {
                String message = "Some tried to connect... failed.";
                server.broadcast(message);
            }
            
            while (((Connection) this).connectionOpen) {
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
    
	protected void handleIncomingMessage(String name, Object msg) {
		if(((Connection) this).connectionOpen == true) {
			super.handleIncomingMessage(name,msg);
		} else {}
	}
      // inherited constructors



	public Connection ( Socket s, Server server ) { super(s, server); }
}