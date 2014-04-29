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
	public Client(String host, int port, List<ChatPlugin> plugins) {

		new History("../log/chatlog.txt", this);
	}
}
