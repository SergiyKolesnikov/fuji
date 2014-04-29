package client; 

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import common.Message;
import common.PrivateMessage;
import common.TextMessage;

public  class  Client {
	private BufferedWriter logWriter;
	
	public Client(String iserverAddress, int iport) {
		try {
			FileWriter fstream = new FileWriter("chat.log");
			logWriter = new BufferedWriter(fstream);
		} catch (IOException e) {
			System.err.println("Could not open the log file.");
		}
	}
	
	private void handleMessage(Message o) {
		original(o);
		if(logWriter != null) {
			try {
				logWriter.write(o.textForLog());
				logWriter.flush();
			} catch (IOException e) { //fail silently...
			}
		}
	}
	
	public void disconnect() {
		original();
		try {
			logWriter.close();
			logWriter = null;
		} catch (IOException e) {
		}
	}
}
