package client;

import common.TextMessage;

import tools.Encryption;
import client.Gui;

public class Client {

	public Client(String[] args) {
		original();
		gui.enableElement("lblNewLabel_1");
		gui.enableElement("rdbtnNewRadioButton_4");
		gui.enableElement("rdbtnReversal");
		gui.enableElement("rdbtnNewRadioButton_5");
	}

	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			if (this.encryptionPlugin == null) {
				fireAddLine(((TextMessage) msg).getContent() + "\n");
			} else {
				fireAddLine(((TextMessage) msg).decryptContent() + "\n");
			}
		}
	}

	public void send(TextMessage text) {

		String plain = "";

		if (text.getEncryptionSelector().equals(Encryption.NONE)) {
			plain = text.getContent();
		} else if (text.getEncryptionSelector().equals(Encryption.ROT13)) {
			plain = Encryption.rot13(text);
		} else if (text.getEncryptionSelector().equals(Encryption.REVERSAL)) {
			plain = Encryption.reverseMessage(text);
		}

		original(new TextMessage(plain));
	}

}
