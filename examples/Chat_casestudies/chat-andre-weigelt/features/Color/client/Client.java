package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;

import client.Color;
import common.TextMessage;

/**
 * simple chat client
 */
public class Client implements Runnable {
	
	private Color colorPlugin = new Color();

	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			TextMessage message = (TextMessage) msg;
			colorPlugin.handleIncomingMessage(message);
			original(message);
		}
	}
	
	public Color getColorPlugin() {
		return this.colorPlugin;
	}
	
	public void send(TextMessage msg) {
		colorPlugin.handleOutgoingMessage(msg);
		original(msg);
	}
	
	

}
