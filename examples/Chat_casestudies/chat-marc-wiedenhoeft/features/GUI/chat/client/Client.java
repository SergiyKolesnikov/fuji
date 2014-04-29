package chat.client;

import chat.client.AuthenticationDialog;
import chat.client.GUI;

public class Client 
{
	public void init(String host, int port)
	{
		original(host, port);
		
		/**
		 * Starts GUI.
		 */
		new GUI(this);
	}
}