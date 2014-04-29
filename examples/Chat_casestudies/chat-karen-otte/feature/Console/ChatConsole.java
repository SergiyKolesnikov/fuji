/**
 * TODO description
 */
import java.util.List;

import javax.swing.text.SimpleAttributeSet;

public class ChatConsole implements ChatLineListener{
	
	public ChatConsole(Client client) {
		client.addLineListener(this);
	}

	@Override
	public void newChatLine(String line) {		
		System.out.println(line);
	}
}