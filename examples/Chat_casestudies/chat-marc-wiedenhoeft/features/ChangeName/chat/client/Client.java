package chat.client;

import chat.TextMessage;

public class Client 
{
	public void send(TextMessage msg) 
	{
		String workString = "";
		
		if(msg.getContent().startsWith("[name]")) //eigenen Namen ändern
		{
			System.out.println("Changing name for: " + msg.getFrom());
			workString = msg.getContent().substring(7);
			System.out.println("Changing name to: " + workString);
			
			this.mUserName = workString;
			
			msg.setContent("User " + msg.getFrom() + " is from now on known as " + this.mUserName);
			msg.setFrom(this.mUserName);
			
			original(msg); //senden
		}
		else
		{
			original(msg); //senden
		}
	}
}