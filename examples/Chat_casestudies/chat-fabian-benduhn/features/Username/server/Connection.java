package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import common.*;
/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */

public class Connection extends Thread {
	private String getName(String name){
		return server.getClientName(this);
	}
	private String getClientName(){
	return server.getClientName(this);
	}
	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(String name, Object rmsg) {
		System.out.println("HALLO");
		TextMessage msg = (TextMessage)rmsg;
		if(msg.isPrivate){
			System.out.println("private");
			server.sendPrivateMessage(msg);
			return;
		}
		if(msg.isChangeNick){
			System.out.println("change");
			server.changeUserName(name,msg);
			return;
		}
		System.out.println("normal");
		original(name,msg);
		}


	


}
