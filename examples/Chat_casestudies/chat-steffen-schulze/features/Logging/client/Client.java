package client;

import java.io.*;
import java.util.Calendar;

import server.Server;

import common.*;

/**
 * simple chat client
 */
public class Client implements Runnable {
	
	private BufferedWriter outputFile;

	public void stop() {
		original();
		
		try {
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeToFile(String line)
	{
		try {
			if (outputFile == null) 
			{
				openFile();
				writeToFile(Calendar.getInstance().getTime().toString());
			}

			outputFile.write(line);
			outputFile.newLine();
			outputFile.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void openFile() throws IOException
	{
		outputFile = new BufferedWriter(new FileWriter(user.getUsername() + ".txt",true));
	}
}
