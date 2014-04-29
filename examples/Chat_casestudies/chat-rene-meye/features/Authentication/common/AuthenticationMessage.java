package common;

import java.io.Serializable;

import client.Client;

import server.Server;

/**
 * serializable message that can be send over the sockets between client and
 * server. 
 */
public class AuthenticationMessage implements Serializable {

	private static final long serialVersionUID = -9161550184923902079L;
	private String username;
	private String passwordHash;

	public AuthenticationMessage(String username, String password) {
		super();
		
		this.username = username;
		this.passwordHash = password;
	}

	public String getPasswordHash() {
		return passwordHash;
	}
	
	public String getUsername(){
		return username;
	}
	
}