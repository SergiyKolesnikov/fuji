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
		if (args.length == 2){
			Client client = new Client(args[0], Integer.parseInt(args[1]),null);
		}else
		throw new RuntimeException("Syntax: ChatClient <host> <port>");
		System.out.println("Loading Plugins ...");
		/* plugins register themselves to client */
		new Cli(client);
	}

	protected ObjectInputStream inputStream;
	protected ObjectOutputStream outputStream;

	protected Thread thread;

	private ArrayList<ChatPlugin> plugins = new ArrayList<ChatPlugin>();
	
	public Client(String host, int port, List<ChatPlugin> plugins) {
		try {
			System.out.println("Connecting to " + host + " (port " + port + ")...");
			Socket s = new Socket(host, port);
			this.outputStream = new ObjectOutputStream((s.getOutputStream()));
			this.inputStream = new ObjectInputStream((s.getInputStream()));
			thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
					newMessage(msg);
				} catch (EOFException e) {
					e.printStackTrace();
					stop();
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
	@Override
	public void newMessage(Object msg) {
		if (msg instanceof Message)
		notifyPlugins((Message) msg);
	}
	
	public void send(Message msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			this.stop();
		}
	}
	
	@Override
	public void loadPlugin(ChatPlugin p) {
		plugins.add(p);
		Collections.sort(plugins, new Comparator<ChatPlugin>() {
			@Override
			public int compare(ChatPlugin p1, ChatPlugin p2) {
				if (p1.getType()<p2.getType())
					return -1;
				if (p1.getType()>p2.getType())
					return 1;
				return 0;
			}
		});
	}
	
	@Override
	public void removePlugin(ChatPlugin p) {
		plugins.remove(p);
	}
	
	@Override
	public Message notifyPlugins(Message msg) {
		for (ChatPlugin p : plugins){
			msg = p.process(msg);
			if (msg ==null) return null;	
		}
		return msg;
	}
	
	public void stop() {
		thread = null;
	}

	
}
