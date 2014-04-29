package client;

import common.*;

/**
 * simple swing gui for the chat client
 */
public class Gui {

	private ITextMessage feat_ROT13_getROT13EncryptedMessage(ITextMessage msg)	{ 
		return new ROT13EncryptedTextMessage(msg);
	}
}
