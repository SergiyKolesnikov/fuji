package client;

import common.TextMessage;

import encryption.ROT13;

/**
 * simple chat client
 */
public class Client implements Runnable {
	
	protected String encodeMessage(String sender, String content)
	{
		if (!sender.equals(Server.NAME)) content = (new ROT13()).encode(content);
		return original(sender, content);
	}
	
	protected TextMessage getMessage(String line)
	{
		line = (new ROT13()).decode(line);
		return original(line);
	}
}
