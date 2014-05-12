package FullGUI;

import java.io.BufferedWriter;
import java.io.FileWriter;



abstract class Log$$Chat {
	
	public void write(String line) {
	}

}



public class Log extends  Log$$Chat  {
	
	private final String LOG_FILE = "log" + System.currentTimeMillis() + ".txt";
	
	private BufferedWriter writer;
	
	public Log() {
		try {
			writer = new BufferedWriter(new FileWriter(LOG_FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(String line) {
		try {
			writer.append(line);
			writer.newLine();
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}