package client;

import java.io.IOException;

/**
 * TODO description
 */
public class Client {
	private String username = "nickname";
	
	public void send(String line) {
		if(line.regionMatches(false, 0 ,"/username", 0, 9)) 
				username = line.substring(10, line.length());
		original(" " + username + ": " + line);
	}
}