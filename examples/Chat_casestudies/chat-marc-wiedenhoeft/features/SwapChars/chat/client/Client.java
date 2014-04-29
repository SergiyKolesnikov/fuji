package chat.client;

import chat.TextMessage;
import chat.Crypto;

public class Client 
{
	protected void handleIncomingMessage(Object msg) 
	{
		if(((TextMessage) msg).getCryptoType() == 1)
		{
			((TextMessage) msg).setContent(Crypto.doSwapCrypto(((TextMessage) msg).getContent()));
		}
		
		original(msg);
	}
	
	public void send(TextMessage msg) 
	{
		msg.setCryptoType(1);
		msg.setContent(Crypto.doSwapCrypto(msg.getContent()));
		
		original(msg);
	}
}