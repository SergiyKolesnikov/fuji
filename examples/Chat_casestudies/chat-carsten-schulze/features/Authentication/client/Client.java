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

public class Client implements Runnable,ChatObserver{
	
	private static final String password = "123456"; // Authentication password
	
	public Client(String host, int port, List<ChatPlugin> plugins) {
			//#if Authentication
				send(new Message(password,(byte) 0x02));
				try {
					Object msg = inputStream.readObject();
					if (msg instanceof Message) {
						if (!((Message) msg).getText().equals(new String("Authentication succesful."))) {
							throw new RuntimeException("Authentication failed.");
						}
						System.out.println("Connection authencitated.");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			//#endif

	}
}
