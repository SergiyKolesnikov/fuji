package client;

import javax.swing.text.BadLocationException;


public class ConsoleUI implements ChatLineListener {

	
	public ConsoleUI(Client client){
		client.addLineListener(this);
	}
	
	@Override
	public void newChatLine(String line) throws BadLocationException {
	System.out.println(line);

	}

}
