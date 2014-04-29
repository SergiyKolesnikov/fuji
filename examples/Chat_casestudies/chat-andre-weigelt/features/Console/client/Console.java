package client;

import client.ChatLineListener;
import client.Client;

public class Console implements ChatLineListener {
	private Client chatClient;

	public Console(Client chatClient) {
		System.out.println("starting gui...");
		System.out
				.println("You are only able to recieve messages - not to write!");
		this.chatClient = chatClient;
		init();
	}

	public void newChatLine(String line) {
		System.out.print(line);
	}

	public void init() {
		this.chatClient.addLineListener(this);

	}

}
