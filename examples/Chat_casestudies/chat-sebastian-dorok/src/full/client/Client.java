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

import client.gui.ConsoleOutput; 

import common.logging.ILogger; 
import common.logging.LoggerFactory; 

import java.util.Random; 

import common.crypto.CryptoFactory; 
import common.crypto.CryptoFactory.CRYPTO_ALGORITHMS; 
import common.message.CryptoMessage; 

/**
 * simple chat client
 */
public   class  Client  implements Runnable {
	

	private static Client client;

	
	private static Gui gui;

	

	private static void  main__wrappee__Base  (String args[]) throws IOException {
		if (args.length < 2)
			throw new RuntimeException("Syntax: ChatClient <host> <port>");

		client = new Client(args[0], Integer.parseInt(args[1]));

		gui = new Gui(client);
	}

	

	private static void  main__wrappee__Console  (String args[]) throws IOException {
		main__wrappee__Base(args);

		ConsoleOutput console = new ConsoleOutput();
		client.addLineListener(console);
	}

	

	public static void main(String args[]) throws IOException {
		main__wrappee__Console(args);
		client.addLineListener(gui);
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
	private void  handleIncomingMessage__wrappee__ChangeUsername  (Object msg) {
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

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		((AbstractMessage) msg).decode(CryptoFactory
				.getCryptoModule(this.cryptoAlgorithm));

		if (this.cryptoAlgorithm.equals(CRYPTO_ALGORITHMS.NO_CRYPT)) {
			// only exchange crypto info when NO_CRYPT selected
			if (msg instanceof StatusMessage) {
				StatusMessage m = (StatusMessage) msg;
				if (m.getStatus().equals(StatusMessage.STATUS.CONNECT_SUCC)) {
					// randomly choose one of the supported algorithms
					// supported encryption modules
					CRYPTO_ALGORITHMS[] supportedAlgorithms = {
							CRYPTO_ALGORITHMS.ROT13, CRYPTO_ALGORITHMS.SWAP };
					Random rand = new Random();
					int idx = rand.nextInt(supportedAlgorithms.length);
					this.cryptoAlgorithm = supportedAlgorithms[idx];
					send(new CryptoMessage(supportedAlgorithms,
							this.cryptoAlgorithm));
				}
				// original behavior mustn't be executed
				return;
			}
		}

		handleIncomingMessage__wrappee__ChangeUsername(msg);
	}

	

	private void  send__wrappee__ChangeUsername  (AbstractMessage msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			this.stop();
		}
	}

	

	public void send(AbstractMessage msg) {
		msg.encode(CryptoFactory.getCryptoModule(this.cryptoAlgorithm));

		send__wrappee__ChangeUsername(msg);
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
	private void  fireAddLine__wrappee__Spamfilter  (TextMessage msg) {
		for (ClientObserver listener : listeners) {
			listener.printNewChatLine(msg);
		}
	}

	

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(TextMessage msg) {
		this.log.log(msg.getFrom() + ":" + msg.getContent());
		fireAddLine__wrappee__Spamfilter(msg);
	}

	

	private void  fireStatusLine__wrappee__Spamfilter  (StatusMessage msg) {
		for (ClientObserver listener : listeners) {
			listener.printStatusMessage(msg);
		}
	}

	

	public void fireStatusLine(StatusMessage msg) {
		this.log.log(msg.getStatus() + ":" + msg.getReason());
		fireStatusLine__wrappee__Spamfilter(msg);
	}

	

	public void stop() {
		thread = null;
	}

	

	private void  connect__wrappee__Spamfilter  (String user, String password) {
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

	

	public void connect(String user, String password) {
		connect__wrappee__Spamfilter(user, password);
		this.log = LoggerFactory.getFileLogger(getUser() + ".log");
		log.log("Connected to " + host + ":" + port + " ...");
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

	

	// by default the logger is doing nothing
	private ILogger log = LoggerFactory.getEmptyLogger();

	

	// at the beginning there is no encryption is enabled
	private CryptoFactory.CRYPTO_ALGORITHMS cryptoAlgorithm = CRYPTO_ALGORITHMS.NO_CRYPT;


}
