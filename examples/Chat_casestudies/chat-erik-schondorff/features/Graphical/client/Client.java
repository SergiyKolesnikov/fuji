package client;

import java.io.IOException;

/**
 * TODO description
 */
public class Client
{
	public static void main(String args[]) throws IOException
	{
		original(args);

		new Gui("Chat " + args[0] + ":" + args[1], client);
	}
}