package client;

import common.Encryption;
import common.TextMessage;

/**
 * simple chat client
 */
public class Client implements Runnable {
	
	private Encryption encrypt = new Encryption();

	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage message = (TextMessage) msg;
			encrypt.handleIncomingMessage(message);
			original(message);
		}
	}
	
	public Encryption getEncrypt() {
		return this.encrypt;
	}
	
	public void send(TextMessage msg) {
		encrypt.handleOutgoingMessage(msg);
		original(msg);
	}
	
	

}
