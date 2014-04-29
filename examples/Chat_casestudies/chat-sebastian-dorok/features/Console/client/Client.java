package client;

import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import client.gui.ConsoleOutput;
import client.gui.Gui;

import common.message.AuthMessage;
import common.message.StatusMessage;
import common.message.AbstractMessage;
import common.message.TextMessage;

/**
 * simple chat client
 */
public class Client implements Runnable {

	public static void main(String args[]) throws IOException {
		original(args);

		ConsoleOutput console = new ConsoleOutput();
		client.addLineListener(console);
	}
}
