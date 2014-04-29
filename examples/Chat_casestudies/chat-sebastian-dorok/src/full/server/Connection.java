package server; 

import java.io.EOFException; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.net.SocketException; 
import common.message.AuthMessage; 
import common.message.StatusMessage; 
import common.message.AbstractMessage; 
import common.message.StatusMessage.STATUS; 
import common.message.TextMessage; 

import common.crypto.CryptoFactory; 
import common.crypto.CryptoFactory.CRYPTO_ALGORITHMS; 
import common.message.CryptoMessage; 

/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public   class  Connection  extends Thread {
	
	protected Socket socket;

	
	protected ObjectInputStream inputStream;

	
	protected ObjectOutputStream outputStream;

	
	private Server server;

	
	private String username;

	

	public Connection(Socket s, Server server) {
		this.socket = s;
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.server = server;
	}

	

	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		// send connection successful state
		send(new StatusMessage("Connection succesfully established.",
				STATUS.CONNECT_SUCC));
		try {
			Object msg = null;
			// readObject blocks until a new object is available
			while ((msg = inputStream.readObject()) != null) {
				handleIncomingMessage(msg);
			}
		} catch (SocketException e) {
		} catch (EOFException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			server.removeConnection(this);
			if (this.username != null) {
				server.broadcast(new StatusMessage(username + " has left.",
						STATUS.USER_LEFT));
			}
			try {
				socket.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void  handleIncomingMessage__wrappee__GUI  (Object msg) {
		if (msg instanceof TextMessage) {
			((TextMessage) msg).setFrom(this.username);
			server.broadcast((AbstractMessage) msg);
		}
		if (msg instanceof AuthMessage) {
			AuthMessage m = (AuthMessage) msg;
			this.username = m.getUser();
			server.broadcast(new StatusMessage(username + " has joined.",
					STATUS.USER_JOINED));
		}
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void  handleIncomingMessage__wrappee__Color  (Object msg) {
		if (msg instanceof TextMessage) {
			String strMsg = ((TextMessage) msg).getContent();
			if (server.doesMessageContainSpam(strMsg)) {
				// spam, notify client
				send(new StatusMessage("Your previous message contains spam. "
						+ "Your message wasn't broadcasted.",
						STATUS.SERVER_WARN));
				// no broadcasting allowed
				return;
			}
		}

		handleIncomingMessage__wrappee__GUI(msg);
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void  handleIncomingMessage__wrappee__PrivateMSG  (Object msg) {
		if (msg instanceof TextMessage) {
			String strMsg = ((TextMessage) msg).getContent();
			// is it a private message
			if (strMsg.contains("/msg")) {
				// split information to identify message and to part
				String[] arr = strMsg.split(" ");
				if (arr.length < 3) {
					server.sendPrivateMSG(new StatusMessage(
							"Wrong private message format."
									+ "Format: /msg <username with no "
									+ "whitespaces> <message>",
							STATUS.SERVER_WARN), this.username);
					return;
				}
				String to = arr[1];
				String content = "";
				for (int i = 2; i < arr.length; i++) {
					content += arr[i];
					if (i < arr.length - 1) {
						content += " ";
					}
				}
				// TODO color
				String from = this.username;
				TextMessage textMsg = new TextMessage(content);
				textMsg.setFrom(from);
				// send message to private receiver
				boolean delivered = server.sendPrivateMSG(textMsg, to);
				if (delivered) {
					// send to your self if private receiver found
					server.sendPrivateMSG(textMsg, from);
				} else {
					// delivery failed
					server.sendPrivateMSG(new StatusMessage(
							"Could not identify private receiver " + to,
							STATUS.SERVER_WARN), from);
				}
				// skip further message processing
				return;
			}
		}

		handleIncomingMessage__wrappee__Color(msg);
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void  handleIncomingMessage__wrappee__Authentication  (Object msg) {
		if (msg instanceof AuthMessage) {
			AuthMessage m = (AuthMessage) msg;
			if (this.server.grantAccess(m.getUser(), m.getPassword())) {
				this.username = m.getUser();
				send(new StatusMessage("Authentication successful. Welcome "
						+ this.username + "!", STATUS.AUTH_SUCC));
			} else {
				send(new StatusMessage("Unknown user or password.",
						STATUS.AUTH_FAIL));

				// original behavior mustn't be executed
				return;
			}
		}
		handleIncomingMessage__wrappee__PrivateMSG(msg);
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void  handleIncomingMessage__wrappee__ChangeUsername  (Object msg) {
		if (msg instanceof TextMessage) {
			String strMsg = ((TextMessage) msg).getContent();
			// is it a private message
			if (strMsg.contains("/username")) {
				// split information to identify message and to part
				String[] arr = strMsg.split(" ");
				if (arr.length < 2) {
					System.out.println("Too few arguments for change username "
							+ "message format. "
							+ "Format: /username <username with "
							+ "no whitespaces>");
					return;
				}
				if (arr.length > 2) {
					System.out
							.println("Too many arguments for change username "
									+ "message format."
									+ "Format: /username <username with "
									+ "no whitespaces>");
					return;
				}
				String newuser = arr[1];
				server.broadcast(new StatusMessage(this.username
						+ " changed name to " + newuser, STATUS.SERVER_INFO));
				this.username = newuser;
				// skip further message processing
				return;
			}
		}

		handleIncomingMessage__wrappee__Authentication(msg);
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(Object msg) {
		((AbstractMessage) msg).decode(CryptoFactory
				.getCryptoModule(this.cryptoAlgorithm));

		if (msg instanceof CryptoMessage) {
			this.cryptoAlgorithm = ((CryptoMessage) msg).getChosenAlgorithm();
			if (this.cryptoAlgorithm != null) {
				send(new StatusMessage("Selected crypto method: "
						+ this.cryptoAlgorithm + ".", STATUS.CONNECT_SUCC));
			} else {
				send(new StatusMessage(
						"No crypto method selected but is needed.",
						STATUS.CONNECT_FAIL));
				return;
			}
		}

		handleIncomingMessage__wrappee__ChangeUsername(msg);
	}

	

	/**
	 * sends a message to the client
	 * 
	 */
	private void  send__wrappee__ChangeUsername  (AbstractMessage msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
			}
			outputStream.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	

	/**
	 * sends a message to the client
	 * 
	 */
	public void send(AbstractMessage msg) {
		msg.encode(CryptoFactory.getCryptoModule(this.cryptoAlgorithm));
		send__wrappee__ChangeUsername(msg);
	}

	

	public String getUserName() {
		return this.username;
	}

	
	// by default there is no encryption enabled
	private CryptoFactory.CRYPTO_ALGORITHMS cryptoAlgorithm = CRYPTO_ALGORITHMS.NO_CRYPT;


}
