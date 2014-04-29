package client;

import common.*;

public class Console{
	private ITextMessage feat_ROT13_getROT13EncryptedMessage(ITextMessage msg)	{ 
		return new ROT13EncryptedTextMessage(msg);
	}
}
