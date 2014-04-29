package encryption;

import common.TextMessage;

import client.Client;
import client.IEncryption;

public abstract class Encryption implements IEncryption {

	public TextMessage extendMessage(TextMessage msg, Client client) {
		msg.setContent(encode(msg.getContent()));
		return msg;
	}

	public TextMessage readMessage(TextMessage msg) {
		msg.setContent(decode(msg.getContent()));
		return msg;
	}

	public void stop() {
	}
}
