package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import server.SpamfilterPlugin;

import common.TextMessage;

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	private SpamfilterPlugin spamfilter = new SpamfilterPlugin();

	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof TextMessage) {
			spamfilter.handleIncomingMessage((TextMessage)msg);
			original(name,msg);
		}
	}

}
