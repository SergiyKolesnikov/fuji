package client;

import common.*;

public class Gui{
	private ITextMessage feat_Swapped_getSwappedEncryptedMessage(ITextMessage msg)	{ 
		return new SwappedEncryptedTextMessage(msg);
	}
}
