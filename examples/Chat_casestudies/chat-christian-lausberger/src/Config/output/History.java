package output; 

import java.io.File; 
import java.io.FileWriter; 
import java.io.IOException; 

import client.IChatLineListener; 



public  class  History  implements IChatLineListener {
	

	private FileWriter writer;

	
	private File file;

	
	
	public History(String host){
		
		this.file = new File("History_" + host);
		try {
			this.writer = new FileWriter(file, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	public void newChatLine(String line) {
		try {
			writer.write(line);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	public void stop() {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
