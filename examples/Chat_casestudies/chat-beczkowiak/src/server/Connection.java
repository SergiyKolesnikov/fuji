package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.IMessage;
import common.TextMessage;

import java.util.HashMap;

import common.LoginMessage;
import common.UserConfirmedMessage;

import crypt.Crypt;



/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
abstract class Connection$$Base$server extends Thread {
	protected Socket socket;
	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;

	protected Server server;
	protected boolean connectionOpen = true;
	protected String clientName = "";

	public Connection$$Base$server(Socket s, Server server) {
		this.socket = s;
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.server = server;
		this.clientName = s.getInetAddress().toString();
	}

	/**
	 * waits for incoming messages from the socket
	 */
        public void run() {
            try {
            	afterConnecting();
                
                while (connectionOpen) {
                    Object msg = inputStream.readObject();
                    handleIncomingMessage(clientName, msg);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
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
	
	protected void afterConnecting() throws IOException, ClassNotFoundException {
		server.broadcast(clientName + " has joined.");
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
		server.broadcast(name + ": " + ((TextMessage) msg).getContent());
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

	public void send(IMessage msg) {
/*	    for (Iterator iterator = plugins.iterator(); iterator.hasNext();) {
                IPlugin plugin = (IPlugin) iterator.next();
                plugin.onSendingMessage((IMessage) msg);
            }
	    
	    boolean check = true;
            
            for (Iterator iterator = plugins.iterator(); iterator.hasNext();) {
                IPlugin plugin = (IPlugin) iterator.next();
                check = check && plugin.isConveyable((IMessage) msg);
            }
            
            if (check) {*/
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



abstract class Connection$$Login$server extends  Connection$$Base$server  {
	protected HashMap users;
    
    protected boolean connectionConfirmed = false;
    
    protected Connection connection;

	public Connection$$Login$server(Socket s, Server server) { super(s, server); 

    	users = new HashMap();
        users.put("Admin", new char[] {'a', 'd', 'm', 'i', 'n'});
        users.put("User", new char[] {'p', 'w'}); }

    
    public void send(IMessage msg) {
    	if (msg instanceof UserConfirmedMessage || ((Connection) this).connectionConfirmed) {
            super.send(msg);
        }
    }
    
    protected void handleIncomingMessage(String name, Object message) {
		if (message instanceof LoginMessage) {
			if (!((Connection) this).connectionConfirmed) {
	            LoginMessage msg = (LoginMessage) message;
	            char[] pw = (char[])users.get(msg.getName());
	            boolean check = true;
	            if (pw != null) {
	                char[] pw2check = msg.getPassword();
	                if (pw2check.length != pw.length) {
	                	check = false;
	                }
	                if (check) {
		                for (int i = 0; i < pw.length; i++) {
		                    if (pw[i] != pw2check[i]) {
		                        ((Connection) this).connectionConfirmed = false;
		                        check = false;
		                    }
		                }
		                ((Connection) this).connectionConfirmed = true;
		                ((Connection) this).clientName = msg.getName();
	                }
	            }
	            send(new UserConfirmedMessage(((Connection) this).connectionConfirmed));
			}
		} else {
			if (((Connection) this).connectionConfirmed) {
	        	super.handleIncomingMessage(name, message);
	    	}
		}
    }
    
    protected void afterConnecting() throws IOException, ClassNotFoundException {
		while (connectionOpen && !((Connection) this).connectionConfirmed) {
            Object msg = inputStream.readObject();
            handleIncomingMessage(clientName, msg);
        }
	}
      // inherited constructors


}



public class Connection extends  Connection$$Login$server  {
	
	protected Crypt crypt;

	public Connection(Socket s, Server server) { super(s, server); 

		((Connection) this).crypt = new Crypt(); }

	
	protected void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof IMessage) {
	        ((IMessage) msg).decode(((Connection) this).crypt);
	    }
	    super.handleIncomingMessage(name, msg);
	}
	
	public void send(IMessage msg) {
		msg.encode(crypt);
		super.send(msg);
	}
      // inherited constructors


}