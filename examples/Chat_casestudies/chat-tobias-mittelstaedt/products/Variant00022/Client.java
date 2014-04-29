package client; 

import java.util.Iterator; 

import client.ChatLineListener; 
import client.Gui; 

import common.TextMessage; 

import tools.Encryption; 

public   class  Client {
	
	
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
