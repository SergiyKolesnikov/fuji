package messageExtension; 

import java.io.BufferedReader; 
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.IOException; 
import java.util.ArrayList; 

import common.TextMessage; 

import client.Client; 
import client.IMessageExtension; 

public  class  SpamFilter  implements IMessageExtension {
	
	ArrayList<String> spam = new ArrayList<String>();

	

	public TextMessage extendMessage(TextMessage msg, Client client) {
		if (spam.isEmpty())
			fillSpamList();
		for (String sw : spam) {
			if (msg.getContent().contains(sw)){
				msg.setContent("****SPAM****");
				break;
			}
		}
		return msg;
	}

	

	private void fillSpamList() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("spam.txt"));
			String line;
			while ((line = reader.readLine()) != null)
				spam.add(line);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	public TextMessage readMessage(TextMessage msg) {
		return msg;
	}

	

	public void stop() {
	}


}
