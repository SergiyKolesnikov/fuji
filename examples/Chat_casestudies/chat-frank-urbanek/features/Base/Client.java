
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
//#if Log
//@import java.util.logging.FileHandler;
//@import java.util.logging.Handler;
//@import java.util.logging.Level;
//@import java.util.logging.Logger;
//#endif

//#if Color
//@import com.frank.common.message.TextMessageColored;
//#endif

/**
 * simple chat client
 */
public class Client implements Runnable {

	protected ObjectInputStream inputStream;

	protected ObjectOutputStream outputStream;

	protected Thread thread;



	protected ClientInfo info = new ClientInfo("epic client");

	private int port = 8081;

	private String host;

	// #if Log
	// @ private static final Logger log = Logger.getLogger(
	// Client.class.getName() );
	// #endif

	private void init() {
		port = Config.PORT;
		host = Config.HOST;

		/*
		 * if (Config.CIPHER == Cipher.Codec.ROT13) { cipher = new
		 * CipherROT13(); } if (Config.CIPHER == Cipher.Codec.SWITCH) { cipher =
		 * new CipherSwitch(); }
		 * 
		 * if (Config.CIPHER == Cipher.Codec.NONE) { cipher = new CipherNone();
		 * }
		 */

		// #if Log
		// @ log.setLevel(Level.INFO);
		// @ Handler handler;
		// @
		// @ try {
		// @ handler = new FileHandler( "log.txt" );
		// @ log.addHandler( handler );
		// @ } catch (SecurityException e1) {
		// @ e1.printStackTrace();
		// @ } catch (IOException e1) {
		// @ e1.printStackTrace();
		// @ }
		// #endif
	}

	public Client() {
		init();
	}

	public void start() {
		try {
			System.out.println("Connecting to " + host + " (port " + port
					+ ")...");
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

	private void handleIncomingMessage(Object msg) {
		if (msg instanceof Message) {

			if (msg instanceof ContentMessage) {
				ContentMessage message = (ContentMessage) msg;
				message = processContentMessage(message);
				if (message.getPayload() instanceof TextMessage) {
					processTextMessage((TextMessage) message.getPayload(), message.getContext());
					// #if Log
					// @ log.log(Level.INFO,
					// message.getContext().getInfo().getName() + ": "
					// +textMessage.getContent());
					// #endif
				}
			}
		} else {
			System.out.println("Client: Unknown Message");
		}
	}

	private ContentMessage processContentMessage(ContentMessage msg) {
		return msg;
	}

	private void processTextMessage(TextMessage msg, MessageContext context) {
		onNewTextMessage(msg, context);
	}

	/*
	 * public void send(String line) { send(new
	 * TextMessage(cipher.encode(line))); }
	 */

	public void send(TextMessage msg) {
		send((Content) msg);
	}

	public void send(Content msg) {
		MessageContext context = new MessageContext(info);
		ContentMessage message = new ContentMessage(context, msg);
		send(message);
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

	/**
	 * listener-list for the observer pattern
	 */
	private ArrayList<ChatLineListener> listeners = new ArrayList<ChatLineListener>();

	/**
	 * addListner method for the observer pattern
	 */
	public void addLineListener(ChatLineListener listener) {
		listeners.add(listener);
	}

	/**
	 * removeListner method for the observer pattern
	 */
	public void removeLineListener(ChatLineListener listner) {
		listeners.remove(listner);
	}

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(String line) {
		for (ChatLineListener listener : listeners) {
			listener.newChatLine(line);
		}
		/*
		 * for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
		 * 
		 * }
		 */
	}

	public void onNewTextMessage(TextMessage msg, MessageContext context) {
		for (ChatLineListener listener : listeners) {
			listener.newTextMessage(msg, context);
		}
	}

	public void stop() {
		thread = null;
	}
}
