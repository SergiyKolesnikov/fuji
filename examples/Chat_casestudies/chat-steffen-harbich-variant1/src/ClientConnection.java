import java.net.Socket; 
import java.util.ArrayList; 
import java.util.List; 

public   class  ClientConnection  extends Connection {
	

	public ClientConnection(Socket s) {
		super(s);
	}

	

	/**
	 * main method. waits for incoming messages.
	 */
	public void  runCommunicationLoop__wrappee__Encryption_Rot13  () {
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

	
	
	protected void runCommunicationLoop() {
		connectionEstablished();
		runCommunicationLoop__wrappee__Encryption_Rot13();
		connectionClosed();
	}

	

	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message received from the sockets
	 */
	private void  handleIncomingMessage__wrappee__Authentication  (Message msg) {
		if (msg instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) msg;
			fireAddLine(txtMsg);
		}
	}

	

	private void handleIncomingMessage(Message msg) {
		if (msg instanceof TextMessage) {
			History.instance.logTextMessage((TextMessage)msg);
		}
		handleIncomingMessage__wrappee__Authentication(msg);
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

	

	private AuthenticationMessage message = new AuthenticationMessage(Application.args[3], Application.args[4]);

	
	
	private void connectionEstablished() {
		send(message);
		
		Message msg = receive();
		
		if (msg instanceof AuthenticationResultMessage) {
			if (((AuthenticationResultMessage) msg).isSuccessful() == false) {
				System.out.println("WARNING: authentication failed!");
				close();
			}
			else {
				setUserName(message.getUserName());
			}	
		}
		else if(msg != null) {
			System.out.println("WARNING: authentication protocol neglected!");
		}
	}

	
	
	private void connectionClosed() {
	}


}
