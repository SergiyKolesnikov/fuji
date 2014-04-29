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
import common.ColoredMessage; 
import common.PrivateMessage; 

import common.CryptoProvider; 
import common.EncryptedMessage; 
import common.XORCryptoProvider; 

import common.SetUsernameMessage; 

public    class   Client {
	
	
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

	
	
	public Client  (String iserverAddress, int iport) {
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
	
		try {
			FileWriter fstream = new FileWriter("chat.log");
			logWriter = new BufferedWriter(fstream);
		} catch (IOException e) {
			System.err.println("Could not open the log file.");
		}
	}

	
	
	public void addChatListener(ChatListener l) {
		if(!chatListeners.contains(l)) {
			chatListeners.add(l);
		}
	}

	
	
	public void removeChatListender(ChatListener l) {
		chatListeners.remove(chatListeners.indexOf(l));
	}

	
	
	 private void  handleMessage__wrappee__Base  (Message o) {
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

	
	
	 private void  handleMessage__wrappee__Color  (Message o) {
		handleMessage__wrappee__Base(o);
		if(o.getClass().equals(ColoredMessage.class)) {
			ColoredMessage m = (ColoredMessage)o;
			for (Iterator i = chatListeners.iterator(); i.hasNext();) {
				((ChatListener) i.next()).handleColoredMessage(m.getFrom(), m.getText(), m.getColor());
			}
		}
	}

	
	
	 private void  handleMessage__wrappee__History  (Message o) {
		handleMessage__wrappee__Color(o);
		if(logWriter != null) {
			try {
				logWriter.write(o.textForLog());
				logWriter.flush();
			} catch (IOException e) { //fail silently...
			}
		}
	}

	
	
	 private void  handleMessage__wrappee__Encryption  (Message o) {
		handleMessage__wrappee__History(o);
		if (o.getClass().equals(EncryptedMessage.class)) {
			handleMessage(((EncryptedMessage)o).decrypt(cryptoProvider));
		}
	}

	
	
	 private void  handleMessage__wrappee__PrivateMessage  (Message o) {
		handleMessage__wrappee__Encryption(o);
		if(o.getClass().equals(PrivateMessage.class)) {
			PrivateMessage m = (PrivateMessage)o;
			for (Iterator i = chatListeners.iterator(); i.hasNext();) {
				((ChatListener) i.next()).handlePrivateMessage(m.getFrom(), m.getText());
			}
		}
	}

	
	
	private void handleMessage(Message o) {
		handleMessage__wrappee__PrivateMessage(o);
		if(o.getClass().equals(SetUsernameMessage.class)) {
			for (Iterator i = chatListeners.iterator(); i.hasNext();) {
				((ChatListener) i.next()).handleUsernameChange(((SetUsernameMessage)o).getOldName(), ((SetUsernameMessage)o).getNewName());
			}
		}
	}

	
	
	public void send  (String text) {
		send(new ColoredMessage(getUsername(), text, color));
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

	

	 private void  disconnect__wrappee__Color  () {
		listener.interrupt();
		try {
			in.close();		
			out.close();
			socket.close();
		} catch (IOException e) {
		}
	}

	
	
	public void disconnect() {
		disconnect__wrappee__Color();
		try {
			logWriter.close();
			logWriter = null;
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

		
	
	
	public String getUsername  () {
		return username;
	}

	
	private String color = "black";

	
	
	public void setColor(String i) {
		color = i;
	}

	
	private BufferedWriter logWriter;

	
	
	private CryptoProvider cryptoProvider = new XORCryptoProvider("lala");

	
	
	public void sendEncrypted(String i) {
		send(new EncryptedMessage(getUsername(), i, cryptoProvider));
	}

	
	
	public void setCryptoProvider(CryptoProvider c) {
		cryptoProvider = c;
	}

	
	public void sendPrivate(String to, String text) {
		send(new PrivateMessage(to, getUsername(), text));
	}

	
	private String username = "User";

	

	public void setUsername(String username) {
		this.username = username;
		send(new SetUsernameMessage("", username));
	}


}
