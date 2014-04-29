package client;

import java.io.IOException;

import common.TextMessage;

/**
 * TODO description
 */
public class Client {
	protected void handleIncomingMessage(Object msg) throws IOException {
		if (msg instanceof TextMessage)
			msg = new TextMessage("<blau>" + ((TextMessage) msg).getContent() + "</blau>");
		original(msg);
	}
}