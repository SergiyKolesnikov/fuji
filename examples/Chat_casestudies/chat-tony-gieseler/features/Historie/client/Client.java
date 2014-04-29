package client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import common.TextMessage;

/**
 * TODO description
 */
public class Client {
	protected void handleIncomingMessage(Object msg) throws IOException {
		original(msg);
		FileWriter fw = new FileWriter("history.log", true);
						
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(((TextMessage) msg).getContent());
		bw.newLine();
		bw.close();
	}
}