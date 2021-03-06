
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * simple chat client
 */
public class ClientConnection extends Connection {

	public ClientConnection(Socket s) {
		super(s);
	}

	/**
	 * main method. waits for incoming messages.
	 */
	public void runCommunicationLoop() {
		try {
			while (connectionOpen) {
				Message msg = receive();
				
				if (msg != null)
					handleIncomingMessage(msg);
			}
		}
		finally {
			// clean up
			close();
		}
	}

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message received from the sockets
	 */
	private void handleIncomingMessage(Message msg) {
		if (msg instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) msg;
			fireAddLine(txtMsg);
		}
	}


	/**
	 * listener-list for the observer pattern
	 */
	private List<ChatLineListener> listeners = new ArrayList<ChatLineListener>();

	public void addLineListener(ChatLineListener listener) {
		listeners.add(listener);
	}

	public void removeLineListener(ChatLineListener listner) {
		listeners.remove(listner);
	}

	public void fireAddLine(TextMessage line) {
		for (ChatLineListener listener : listeners) {
			listener.newChatLine(line);
		}
	}

}
