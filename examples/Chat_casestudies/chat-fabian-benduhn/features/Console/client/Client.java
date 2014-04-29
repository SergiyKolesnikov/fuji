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
import common.encryption.*;


/**
 * simple chat client
 */
public class Client implements Runnable {


	private void initUI(String host, int port){
		new ConsoleUI(this);
	}
}
