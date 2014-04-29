package chat.client;

import chat.TextMessage;
import chat.client.History;

public class Client 
{
	protected void handleIncomingMessage(Object msg) 
	{
		if(this.isAuthenticated == true)
		{
			if (msg instanceof TextMessage) 
			{
				/**
				 * Append to the history. 
				 */
				History.append(
						" " + 
						((TextMessage) msg).getFrom() + 
						": " +
						((TextMessage) msg).getContent() );
			}
		}		
		original(msg);
	}
}