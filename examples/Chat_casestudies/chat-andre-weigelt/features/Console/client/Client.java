package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import common.TextMessage;

/**
 * simple chat client
 */
public class Client implements Runnable {

	public Client(String host, int port) {
		new Console(this);
		
	}

	
}
