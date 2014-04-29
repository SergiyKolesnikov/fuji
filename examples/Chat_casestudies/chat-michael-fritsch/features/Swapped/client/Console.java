package client;

import common.*;

public class Console{
	private ITextMessage feat_Swapped_getSwappedEncryptedMessage(ITextMessage msg)	{ 
		return new SwappedEncryptedTextMessage(msg);
	}
}
