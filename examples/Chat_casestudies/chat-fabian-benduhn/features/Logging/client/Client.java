package client;

import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.text.BadLocationException;


import common.TextMessage;

import common.logging.ChatLogger;

/**
 * simple chat client
 */
public class Client implements Runnable {
	
	
	private ChatLogger logger;






	private void ClientConstructor(String host, int port) {
		
		original(host,port);
		logger = new ChatLogger("chatlog.txt");

	}



	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 * @throws BadLocationException 
	 * @throws IOException 
	 */
	private void handleLine(String line,boolean encrypted)throws Exception{
		original(line,encrypted);
			log(line);

	}
	
	private void log(String line) throws IOException {
		logger.addMessage(line);
		
	}


}
