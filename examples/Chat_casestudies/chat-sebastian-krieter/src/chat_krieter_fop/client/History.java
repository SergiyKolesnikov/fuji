package client; 

import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileWriter; 
import java.io.IOException; 

public  class  History {
	
	
	private File textFile = null;

	
	private BufferedWriter writer = null;

	

	public void close() {
		if (writer != null) {
			try {
				writer.close();
				writer = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	

	public void newChatLine(Message msg) {
		if (writer != null && msg.getSender() != null) {
			try {
				writer.write(msg.getLine());
				writer.newLine();
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	

	public void setUsername(String username) {
		textFile = new File(username + "_history.log");
		try {
			if (writer != null) {
				writer.close();
			}
			writer = new BufferedWriter(new FileWriter(textFile, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
