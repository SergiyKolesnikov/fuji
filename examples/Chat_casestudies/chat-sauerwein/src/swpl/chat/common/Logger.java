package swpl.chat.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



public class Logger {
	private static final String NEWLINE = System.getProperty("line.separator");
	
	private BufferedWriter fileWriter;
	
	public Logger(String fileName) {
		try {
			System.out.println(fileName);
			fileWriter = new BufferedWriter(new FileWriter(fileName));
		} catch (IOException exc) {
			System.out.println("Warning: Logging failed");
		}
	}
	
	public void log(String msg) {
		try {
			fileWriter.write(msg + NEWLINE);
			fileWriter.flush();
			System.out.println(msg);
		} catch (IOException e) {
			System.out.println("Warning: Logging failed");
		}
	}
	
	public void close() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Warning: Logging failed");
		}
	}
}