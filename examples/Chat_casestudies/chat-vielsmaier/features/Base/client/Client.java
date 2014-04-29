package client;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import common.JoinMessage;
import common.Message;
import common.TextMessage;

public class Client {
	
	private String serverAddress;
	
	private int port;
	
	private boolean disconnected = false;
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	
	private Thread listener;
	
	private List chatListeners;
	
	public Client() {
		this("localhost", 8017);
	}
	
	public Client(String iserverAddress, int iport) {
		chatListeners = new LinkedList();
		serverAddress = iserverAddress;
		port = iport;
		
		listener = new Thread(new Runnable() {
			
			public void run() {
				while(!disconnected && !socket.isClosed() && !Thread.interrupted()) {
					try {
						Object o = in.readObject();
						if(o instanceof Message) {
							handleMessage((Message)o);
						}
					} catch (EOFException e) {
						disconnected = true;
					} catch (SocketException e) {
						disconnected = true;
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		listener.setDaemon(true);		
	}
	
	public void addChatListener(ChatListener l) {
		if(!chatListeners.contains(l)) {
			chatListeners.add(l);
		}
	}
	
	public void removeChatListender(ChatListener l) {
		chatListeners.remove(chatListeners.indexOf(l));
	}
	
	private void handleMessage(Message o) {
		if(o.getClass().equals(TextMessage.class)) {
			TextMessage m = (TextMessage)o;
			for (Iterator i = chatListeners.iterator(); i.hasNext();) {
				((ChatListener) i.next()).handleLine(m.getFrom(), m.getText());
			}
		} else if(o.getClass().equals(JoinMessage.class)) {
			for (Iterator i = chatListeners.iterator(); i.hasNext();) {
				((ChatListener) i.next()).handleJoin(((JoinMessage) o).getUsername());
			}
		}
	}

	public void send(String i) {
		send(new TextMessage(getUsername(), i));
	}
	
	private void send(Message m) {
		try {
			out.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			socket = new Socket(serverAddress, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			listener.start();
		} catch (IOException e) {
			System.err.println("Error while connecting:");
			e.printStackTrace();
		}
	}

	public void disconnect() {
		listener.interrupt();
		try {
			in.close();		
			out.close();
			socket.close();
		} catch (IOException e) {
		}
	}
	
	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isDisconnected() {
		return disconnected;
	}
	
	public String getUsername() {
		return "lala";
	}
}
