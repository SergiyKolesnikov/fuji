package server;

import java.io.FileWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;
import common.*;

public class Connection{
	
	/**
	 * writes the message to history log file
	 * 
	 * @param clientName
	 * @param msg
	 */
	public void addToHistory(ITextMessage msg){
		try {
			String fileName = "ChatHistory.txt";
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(String.format("%s - %s: %s\r\n", new Timestamp(new Date().getTime()), socket.getInetAddress().toString(), msg.toString()));
			writer.close();
		}
		catch(Exception ex){
		}
	}
}