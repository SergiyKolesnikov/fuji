package server; 

import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.util.HashMap; 
import java.util.Iterator; 
import java.util.Map; 

import common.JoinMessage; 

import common.Message; 
import common.TextMessage; 
import common.PrivateMessage; 
import common.SetUsernameMessage; 

public   class  Connection {
	
	
	
	private Socket socket;

	
	private Server server;

	
	private ObjectInputStream in;

	
	private ObjectOutputStream out;

	

	
	private Thread listener;

	
	
	private boolean disconnected = false;

	
	private String username = "User";

	
	
	public String getUsername  () {
		return username;
	}

	

	public Connection(Socket isocket, Server iserver) {
		socket = isocket;
		server = iserver;
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		final Connection self = this;
		listener = new Thread(new Runnable() {
			
			public void run() {
				while(!disconnected && !Thread.interrupted()) {
					try {
						Object o = in.readObject();
						if(o instanceof Message) {
							handleMessage((Message)o);
						}
					} catch(java.io.EOFException e ) {
						disconnected = true;
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				disconnect();
				server.disconnect(self);
			}
		});
		listener.setDaemon(true);
		listener.start();
	}

	
	
	 private void  handleMessage__wrappee__Encryption  (Message o) {
		if(o instanceof TextMessage) {
			((TextMessage)o).setFrom(username);
			server.broadcast(o);
		}
	}

		
	 private void  handleMessage__wrappee__PrivateMessage  (Message o) {
		if(o instanceof PrivateMessage) {
			server.sendTo(((PrivateMessage)o).getTo(), o);
		} else {
			handleMessage__wrappee__Encryption(o);
		}
	}

		
	private void handleMessage(Message o) {
		handleMessage__wrappee__PrivateMessage(o);
		if(o instanceof SetUsernameMessage) {
			SetUsernameMessage incoming = (SetUsernameMessage) o;
			SetUsernameMessage outgoing = new SetUsernameMessage(username, incoming.getNewName());
			username = incoming.getNewName();
			server.broadcast(outgoing);
		}
	}

	

	public void send(Message m) {
		try {
			out.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	public void disconnect() {
		listener.interrupt();
		try {
			listener.join();
			in.close();		
			out.close();
			socket.close();
		} catch (InterruptedException e) {
		} catch (IOException e) {
		}
	}


}
