package server; 

import java.io.EOFException; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.net.SocketException; 

import server.ChatUser; 

import client.Client; 

import common.*; 
import common.TextMessage; 

import common.AuthenticationMessage; 

import common.ServerCommandMessage; 

/**
 * TODO description
 */
public   class  Connection  extends Thread {
	
	protected Socket socket;

	
	protected ObjectInputStream inputStream;

	
	protected ObjectOutputStream outputStream;

	
	private Server server;

	

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
	 * waits for incoming messages from the socket
	 */
	public void run() {
		String clientName = getClientName();
		try {
			server.broadcast(clientName + " has joined. - ");
			Object msg = null;
			while ((msg = inputStream.readObject()) != null) {
				clientName = getClientName();
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
			server.broadcast(clientName + " has left. - ");
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	
	
	 private String  getClientName__wrappee__Logging  (){
		return socket.getInetAddress().toString();
	}

	
	
	private String getClientName(){
		if(user != null){
			return user.realName+" ("+user.username+")";
		}
		return getClientName__wrappee__Logging();
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	 private void  handleIncomingMessage__wrappee___Base  (String name, Object msg) {
				
		if (msg instanceof TextMessage){
			server.broadcast(name + " - " + ((TextMessage) msg).getContent());
		}
	}

	
	 private void  handleIncomingMessage__wrappee__Logging  (String name, Object msg) {
		
		if(msg instanceof TextMessage){
			TextMessage textMessage = (TextMessage) msg;
	        for(String badWord: server.blacklist){
	        	if(textMessage.getContent().indexOf(badWord)>=0){
	        		System.out.println("Drop Message for Spamfiltering.");
	        		return;
	        	}
		    }	
		}
		
		handleIncomingMessage__wrappee___Base(name, msg);	
	}

	
	
	 private void  handleIncomingMessage__wrappee__MD5  (String name, Object msg) {
		if (msg instanceof AuthenticationMessage){
			System.out.print("Got Auth Request from: "+((AuthenticationMessage)msg).getUsername());
			ChatUser requestingUser = server.authenticate((AuthenticationMessage)msg);
			if(requestingUser != null){
				System.out.println(" --> Success for "+requestingUser.realName);
				server.authenticatedConnections.put(this, true);
				server.connectedUsers.put(this,requestingUser);
				user = requestingUser;
			}else{
				System.out.println(" --> You're out!");
				server.authenticatedConnections.put(this, false);
				server.connectedUsers.put(this,null);
				user = null;
			}
		}
		
		Boolean isAuthenticated = server.authenticatedConnections.get(this);
		if (isAuthenticated instanceof Boolean && isAuthenticated.booleanValue()){
			handleIncomingMessage__wrappee__Logging(name, msg);
		}else{
			System.out.println("Drop message of unauthorized user!");
		};
	}

	
	 private void  handleIncomingMessage__wrappee__ServerCommands  (String name, Object msg) {
		if (msg instanceof ServerCommandMessage) {
			ServerCommandMessage commandMsg = (ServerCommandMessage) msg;
			System.out.println("Got Server Command: "+commandMsg.getCommand()+ " ("+commandMsg.getOptions()+")");
		}
		handleIncomingMessage__wrappee__MD5(name, msg);
	}

	
	 private void  handleIncomingMessage__wrappee__SendUserSpecificMessage  (String name, Object msg) {
		if (msg instanceof ServerCommandMessage && ((ServerCommandMessage)msg).getCommand().equalsIgnoreCase("msg")) {
			ServerCommandMessage commandMsg = (ServerCommandMessage) msg;
			
			//TODO: Hier k�nnte man mal darauf achten, dass auch User mit Space im Namen m�glich werden.
			int spacePosition = commandMsg.getOptions().indexOf(" ");
			if(spacePosition >= 1){
				String realName = commandMsg.getOptions().substring(0, spacePosition);
				String message = commandMsg.getOptions().substring(spacePosition);
				server.sendToRealName(realName, name + " - " + message);
			}
		}
		handleIncomingMessage__wrappee__ServerCommands(name, msg);
	}

	
	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof ServerCommandMessage && ((ServerCommandMessage)msg).getCommand().equalsIgnoreCase("username")) {
			ServerCommandMessage commandMsg = (ServerCommandMessage) msg;
			if(this.user != null ){
				this.user.realName = commandMsg.getOptions();
			}
		}
		handleIncomingMessage__wrappee__SendUserSpecificMessage(name, msg);
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
		}
	}

	
	public ChatUser user;


}
