package client; 

import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.ConnectException; 
import java.net.Socket; 

import common.Commands; 
import common.TextMessage; 

public   class  Client  implements Runnable {
	

	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");
		
		INSTANCE = new Client(args[0], Integer.parseInt(args[1]));
	}

	
	
	private static Client INSTANCE;

	

	protected ObjectInputStream inputStream = null;

	
	protected ObjectOutputStream outputStream = null;

	
	protected Thread thread;

	
	
	private static String username = null;

	

	private Client(String host, int port) {
		Socket s = null;
		try {
			Socket ts = new Socket(host, port);			
			outputStream = new ObjectOutputStream((ts.getOutputStream()));
			inputStream = new ObjectInputStream((ts.getInputStream()));			
			s = ts;			
		} catch (ConnectException e) {
			newChatLine(Message.info("No server found."));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (s != null) {
			String loginname = login();
			if (loginname != null) {
				setUsername(loginname);
				thread = new Thread(this);
				thread.start();
			} else {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	

	/**
	 * main method. waits for incoming messages.
	 */
	public void run() {
		Thread thisthread = Thread.currentThread();
		while (thread == thisthread) {
			try {
				handleIncomingMessage(inputStream.readObject());
			} catch (Exception e) {
				break;
			}
		}
		newChatLine(Message.info("Connection lost."));
		stop();
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	 private void  handleIncomingMessage__wrappee__Spamfilter  (Object msg) {
		if (msg != null && msg instanceof TextMessage) {
			Message m = new Message(username, (TextMessage) msg);
			
			incomingAction(m);
			newChatLine(m);
		}
	}

	
	
	protected void handleIncomingMessage(Object msg) {
		handleIncomingMessage__wrappee__Spamfilter(msg);
		if (msg != null && msg instanceof Integer) {
			if (curCommand == Commands.COMMAND_USERNAME && Commands.RESPONSE_OK == (Integer) msg) {
				setUsername(curArgs[0]);
			}
		}
	}

	
	
	private static void stop() {
		INSTANCE.thread = null;
	}

	
	
	public static boolean canSend() {
		return INSTANCE.thread != null && INSTANCE.outputStream != null;
	}

	
	
	private static void sendObject(Object msg) {
		try {
			INSTANCE.outputStream.writeObject(msg);
			INSTANCE.outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	

	 private static void  sendMessage__wrappee__Spamfilter  (String line) {
		if (canSend()) {
			sendObject(toTextMessage(line));
		}
	}

	
	
	public static void sendMessage(String line) {
		if (line.startsWith("/")) {
			if (canSend()) {
				String[] pcmnd = line.split(" ", 3);
				String cmnd = pcmnd[0].toLowerCase();
				if (USERNAME_CHANGE.equals(cmnd)) {
					INSTANCE.changeUsername(pcmnd[1]);
				} else if (PRIVATE_MESSAGE.equals(cmnd)) {
					privateMessage(pcmnd[1], pcmnd[2]);
				} else {
					sendObject(toTextMessage(line));
				}
			}
		} else {
			sendMessage__wrappee__Spamfilter(line);
		}
		
	}

	
	
	private static TextMessage toTextMessage(String line) {
		return new TextMessage(username, line);		
	}

	
	
	 private static void  close__wrappee__Authentication  () {
		if (canSend()) {
			sendObject(null);
		}
		stop();		
        System.exit(0);
	}

	
	
	public static void close() {
		history.close();
		close__wrappee__Authentication();
	}

	
	
	 private void  setUsername__wrappee__Base  (String name) {
		username = name;
	}

	
	
	 private void  setUsername__wrappee__Authentication  (String name) {
		gui.setUsername(name);
		setUsername__wrappee__Base(name);
	}

	
	
	private void setUsername(String name) {
		history.setUsername(name);
		setUsername__wrappee__Authentication(name);
	}

	
	
	 private void  incomingAction__wrappee__History  (Message msg) {
		
	}

	
	
	private void incomingAction(Message msg) {
		SpamFilter.filter(msg);
		incomingAction__wrappee__History(msg);
	}

	
	
	 private void  newChatLine__wrappee__Base  (Message msg) {
		
	}

	
	
	 private void  newChatLine__wrappee__Authentication  (Message msg) {
		gui.newChatLine(msg);
		newChatLine__wrappee__Base(msg);
	}

	
	
	private void newChatLine(Message msg) {
		history.newChatLine(msg);
		newChatLine__wrappee__Authentication(msg);
	}

	
	
	private String login  () {
		try {
			outputStream.writeInt(Commands.COMMAND_LOGIN);
			outputStream.writeObject(password);
			outputStream.flush();
			
			if (Commands.RESPONSE_OK == inputStream.readInt()) {
				return inputStream.readObject().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	private final Gui gui = new Gui();

	
	private final String password = "123456";

	
	
	private static final History history = new History();

	
	
	private static final String
		USERNAME_CHANGE = "/nick",
		PRIVATE_MESSAGE = "/msg";

	
	
	private int curCommand = 0;

	
	private String[] curArgs = null;

	
	
	private void changeUsername(String name) {
		try {
			outputStream.writeObject(Commands.COMMAND_USERNAME);
			outputStream.writeObject(name);
			outputStream.flush();
			curArgs = new String[]{name};
			curCommand = Commands.COMMAND_USERNAME;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	private static void privateMessage(String receiver, String line) {
		TextMessage msg = toTextMessage("@" + receiver + ": " + line);
		msg.setReceiver(receiver);
		sendObject(msg);
	}


}
