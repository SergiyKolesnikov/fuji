package client;

import java.util.Iterator;

import client.ChatLineListener;
import client.Gui;

/**
 * TODO description
 */
public class Client {
	
	private static Gui gui = null;
	
	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		Client client = new Client(args[0], Integer.parseInt(args[1]));
		new Gui("Chat " + args[0] + ":" + args[1], client);
	}

	public void fireAddLine(String line) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ChatLineListener listener = (ChatLineListener) iterator.next();
			listener.newChatLine(line);
		}

	}

}