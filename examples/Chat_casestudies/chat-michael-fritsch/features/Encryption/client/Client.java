package client;

import common.*;

/**
 * simple chat client
 */
public class Client {

	private String feat_Encryption_getMessage(Object msg) {
		if (msg instanceof EncriptedTextMessage) 
			return Encrypter.decrypt((ITextMessage)msg);
		else 
			return ((ITextMessage)msg).toString();
	}

}
