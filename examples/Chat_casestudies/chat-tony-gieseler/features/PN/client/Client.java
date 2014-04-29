package client;

import java.io.IOException;

import common.TextMessage;

/**
 * TODO description
 */
public class Client {
	protected void handleIncomingMessage(Object msg) throws IOException {
		if (msg instanceof TextMessage) {
			String str = ((TextMessage) msg).getContent();
			if(str.regionMatches(false, username.length()+16, "/msg", 0, 4)) {
				if(str.regionMatches(false, username.length()+21, username, 0, username.length()))
					original(new TextMessage(str.substring(0, username.length()+14) + ": " + str.substring(2*username.length()+21)));
			} else {
				original(msg);
				
			}
		}
	}
}