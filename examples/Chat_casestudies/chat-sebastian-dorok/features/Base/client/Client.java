package client;

import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import client.gui.Gui;

import common.message.AuthMessage;
import common.message.StatusMessage;
import common.message.AbstractMessage;
import common.message.TextMessage;

/**
 * simple chat client
 */
public class Client implements Runnable {

	private static Client client;
	private static Gui gui;

	public static void main(String args[]) throws IOException {
		if (args.length < 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		client = new Client(args[0], Integer.parseInt(args[1]));

		gui = new Gui(client);
	}

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;

	private String host;
	private int port;
	private String user;
	private String password;

	public Client(String host, int port) {
		setHost(host);
		setPort(port);
	}

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		try {
			Thread thisthread = Thread.currentThread();
			while (thread == thisthread) {
				try {
					Object msg = inputStream.readObject();
					handleIncomingMessage(msg);
				} catch (EOFException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			thread = null;
			try {
				outputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			fireAddLine((TextMessage) msg);
		}
		if (msg instanceof StatusMessage) {
			StatusMessage m = (StatusMessage) msg;
			switch (m.getStatus()) {
			case CONNECT_SUCC:
				fireStatusLine((StatusMessage) msg);
				send(new AuthMessage(user, password));
				break;
			case CONNECT_FAIL:
				fireStatusLine((StatusMessage) msg);
				stop();
				break;
			default:
				fireStatusLine((StatusMessage) msg);
				break;
			}
		}
	}

	public void send(AbstractMessage msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			this.stop();
		}
	}

	/**
	 * listener-list for the observer pattern
	 */
	private ArrayList<ClientObserver> listeners = new ArrayList<ClientObserver>();

	/**
	 * addListner method for the observer pattern
	 */
	public void addLineListener(ClientObserver listener) {
		listeners.add(listener);
	}

	/**
	 * removeListner method for the observer pattern
	 */
	public void removeLineListener(ClientObserver listner) {
		listeners.remove(listner);
	}

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(TextMessage msg) {
		for (ClientObserver listener : listeners) {
			listener.printNewChatLine(msg);
		}
	}

	public void fireStatusLine(StatusMessage msg) {
		for (ClientObserver listener : listeners) {
			listener.printStatusMessage(msg);
		}
	}

	public void stop() {
		thread = null;
	}

	public void connect(String user, String password) {
		if (user == null || user.isEmpty()) {
			this.user = "anonymous" + Math.round(Math.random() * 100);
		} else {
			this.user = user;
		}
		this.password = password;
		Socket s = null;
		try {
			s = new Socket(host, port);
			this.outputStream = new ObjectOutputStream((s.getOutputStream()));
			this.inputStream = new ObjectInputStream((s.getInputStream()));
			this.thread = new Thread(this);
			this.thread.start();
		} catch (Exception e) {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return this.user;
	}
}
