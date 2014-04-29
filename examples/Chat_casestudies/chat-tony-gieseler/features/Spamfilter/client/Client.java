package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import common.TextMessage;
/**
 * TODO description
 */
public class Client {
	protected void handleIncomingMessage(Object msg) throws IOException {
		if (msg instanceof TextMessage) {
			ArrayList<String> list = new ArrayList<String>();
			BufferedReader in = new BufferedReader(new FileReader("Sperrliste.txt"));
			
			String zeile = "";
			while ((zeile = in.readLine()) != null)
				list.add(zeile);
				
			in.close();
			
			Iterator<String> it = list.iterator();
			String s = "";
			while(it.hasNext())
				s = ((TextMessage) msg).getContent().replaceAll(it.next().toString(), "***");
			
			original(new TextMessage(s));
		}
	}
}