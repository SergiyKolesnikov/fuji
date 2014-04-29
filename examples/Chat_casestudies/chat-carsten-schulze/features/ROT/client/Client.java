package client;

import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import common.Message;


/**
 * simple chat client
 */
public class Client implements Runnable,ChatObserver{
	public static void main(String args[]) throws IOException {
		original(); 
		new Encryption("ROT",(char)0x36 ,client);
	}
	public void send(Message msg) {
		for (ChatPlugin p : plugins){
			if (p instanceof Encryption){
				msg = new Message(((Encryption) p).encrypt(msg.getText()), msg.getStatus());
			}
		}
		original();
	}
}
