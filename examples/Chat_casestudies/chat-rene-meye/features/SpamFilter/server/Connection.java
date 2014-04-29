package server;

import common.TextMessage;

/**
 * TODO description
 */
public class Connection {
	private void handleIncomingMessage(String name, Object msg) {
		
		if(msg instanceof TextMessage){
			TextMessage textMessage = (TextMessage) msg;
	        for(String badWord: server.blacklist){
	        	if(textMessage.getContent().indexOf(badWord)>=0){
	        		System.out.println("Drop Message for Spamfiltering.");
	        		return;
	        	}
		    }	
		}
		
		original(name, msg);	
	}
}