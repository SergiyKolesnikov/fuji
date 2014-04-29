package client;

import common.TextMessage;

import client.Client;
import client.IMessageExtension;

public class SenderExtension implements IMessageExtension {

	public TextMessage extendMessage(TextMessage msg, Client client) {
		msg.setSender(client.getUser());
		return msg;
	}

	public TextMessage readMessage(TextMessage msg) {
		String text = msg.getSender() + " - " + msg.getContent();
		msg.setContent(text);
		return msg;
	}
	
	public void stop() {
	}
}
