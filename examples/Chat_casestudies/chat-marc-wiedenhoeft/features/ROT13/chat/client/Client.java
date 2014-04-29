package chat.client;

import chat.TextMessage;
import chat.Crypto;

public class Client 
{
	protected void handleIncomingMessage(Object msg) 
	{
		if(((TextMessage) msg).getCryptoType() == 2)
		{		
			((TextMessage) msg).setContent(Crypto.doROT13Crypto(((TextMessage) msg).getContent()));
		}
		
		original(msg);
	}

	public void send(TextMessage msg) 
	{
		msg.setCryptoType(2);		
		msg.setContent(Crypto.doROT13Crypto(msg.getContent()));
		
		original(msg);
	}
}