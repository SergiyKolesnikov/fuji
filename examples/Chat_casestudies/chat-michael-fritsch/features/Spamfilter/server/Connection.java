package server;

import common.*;

public class Connection {

	private String[] _spamList = {"Viagra", "Penis", "Slut"};
	
	/**
	 * checks whether the message contains spam
	 * @param msg message to be checked
	 * @return true if the message contains a word from the spam list
	 */
	private boolean containsSpam(ITextMessage msg){
		
		String message = msg.getContent();
		//#if Encryption
		if (msg instanceof EncriptedTextMessage) {
			message = Encrypter.decrypt((ITextMessage)msg);
		}
		//#endif
		message = message.toLowerCase();
		
		for(String word : _spamList){
			if (message.contains(word.toLowerCase())) 
				return true;
		}
			
		return false;
	}
}
