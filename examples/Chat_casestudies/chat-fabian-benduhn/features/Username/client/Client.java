package client;

import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.text.BadLocationException;


import common.*;



/**
 * simple chat client
 */
public class Client implements Runnable {


	 

	public void send(String line) {
		System.out.println("line "+line);
		TextMessage msg = new TextMessage(line,false);
		if(line.contains("/username")){
			System.out.println("uss");
			msg.isChangeNick=true;
		}
		else if(line.contains("/msg")){
			System.out.println("msgs");
			msg.isPrivate=true;
		}
		send(msg);
	}

	
}
