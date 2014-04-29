package client;

import encryption.SwapFirstLetters;

/**
 * simple chat client
 */
public class Client implements Runnable {
	
	protected String encodeMessage(String sender, String content)
	{
		if (!sender.equals(Server.NAME)) content = (new SwapFirstLetters()).encode(content);
		return original(sender, content);
	}

	public void send(String line) {
		line = (new SwapFirstLetters()).decode(line);
		original(line);
	}
}
