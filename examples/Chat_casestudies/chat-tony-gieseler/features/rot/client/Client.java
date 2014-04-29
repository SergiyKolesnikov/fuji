package client;

import java.io.IOException;

import common.TextMessage;

/**
 * TODO description
 */
public class Client {
	protected void handleIncomingMessage(Object msg) throws IOException {
		if (msg instanceof TextMessage)
			msg = new TextMessage("<rot>" + ((TextMessage) msg).getContent() + "</rot>");
		original(msg);
	}
}