package server;

import server.ChatUser;

import common.AuthenticationMessage;

/**
 * TODO description
 */
public class Connection {
	public ChatUser user;
	
	private void handleIncomingMessage(String name, Object msg) {
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
			original(name, msg);
		}else{
			System.out.println("Drop message of unauthorized user!");
		};
	}
	
	private String getClientName(){
		if(user != null){
			return user.realName+" ("+user.username+")";
		}
		return original();
	}
}