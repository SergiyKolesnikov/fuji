package server;

import java.util.HashMap;

import common.AuthenticationMessage;

import server.ChatUser;
import server.Connection;


/**
 * TODO description
 */
public class Server {
	private Vector<ChatUser> validUsers = new Vector<ChatUser>();
	public HashMap<Connection, Boolean> authenticatedConnections = new HashMap<Connection, Boolean>();
	public HashMap<Connection, ChatUser> connectedUsers = new HashMap<Connection, ChatUser>();
	
	private void initStuff(){
		validUsers.add(new ChatUser(
						"meye",
						new AuthenticationMessage("", "HalloWelt").getPasswordHash(),
						"Rene"
		));
		validUsers.add(new ChatUser(
						"tommy",
						new AuthenticationMessage("", "1").getPasswordHash(),
						"Tommy"
		));
		validUsers.add(new ChatUser(
				"alice",
				new AuthenticationMessage("", "1").getPasswordHash(),
				"Alice"
		));
		
		original();


	}
	
	public ChatUser authenticate(AuthenticationMessage authenticationMessage) {
		for (ChatUser validUser : validUsers) {
			System.out.println("Checking: "+authenticationMessage.getUsername() +" && "+validUser.username);
			System.out.println("Message Hash:"+authenticationMessage.getPasswordHash());
			System.out.println("Valid User:"+validUser.passwordHash);
			System.out.println("---");
			if(validUser.username.equals(authenticationMessage.getUsername())
					&& validUser.passwordHash.equals(authenticationMessage.getPasswordHash())){
				return validUser;
			}
		}
		
		return null;
	}
}