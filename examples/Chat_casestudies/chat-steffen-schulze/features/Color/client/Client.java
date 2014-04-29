package client;

import common.*;

/**
 * simple chat client
 */
public class Client implements Runnable {
	
	private User getUser(String sender)
	{
		return Users.getUser(sender);
	}
	
	private String getUserColor(String sender)
	{
		return getUser(sender).getColor();
	}
	
	private String getColoredMessage(String sender, String msg)
	{
		String color = getUserColor(sender);
		msg = "<" + color +">" + msg + "</" + color +">";
		return original(sender, msg);
	}
	
}
