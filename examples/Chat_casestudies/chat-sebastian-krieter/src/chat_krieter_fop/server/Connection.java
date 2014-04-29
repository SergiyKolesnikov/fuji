package server; 

import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.net.SocketException; 

import common.Commands; 
import common.TextMessage; 

public   class  Connection  extends Thread {
	
	protected Socket socket;

	
	protected ObjectInputStream inputStream;

	
	protected ObjectOutputStream outputStream;

	
	private Server server;

	
	
	private String username;

	

	public Connection(Socket s, Server server) {
		this.socket = s;
		this.server = server;
		
		try {
			inputStream = new ObjectInputStream((s.getInputStream()));
			outputStream = new ObjectOutputStream((s.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		username = UsernameManager.allocate();
		
		if (waitForLogin()) {
			server.broadcast(new TextMessage(TextMessage.SERVER_MESSAGE, username + " has joined."));
			server.addConnection(username, this);
			
			start();
		} else {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	

	/**
	 * waits for incoming messages from the socket
	 */
	public void run() {
		try {
			Object msg = null;
			while ((msg = inputStream.readObject()) != null) {
				handleIncomingMessage(msg);
			}
		} catch (SocketException e) {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!socket.isClosed()) {
				server.removeConnection(username);
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (username != null) {
					server.broadcast(new TextMessage(TextMessage.SERVER_MESSAGE, username + " has left."));
					UsernameManager.remove(username);
				}
			}
		}
	}

	

	private void handleIncomingMessage  (Object msg) {
		if (msg instanceof TextMessage) {
			server.broadcast((TextMessage) msg);
		} else if (msg instanceof Integer) {
			int cmnd = (Integer) msg;
			if (cmnd == Commands.COMMAND_USERNAME) {
				String newName = null;
				try {
					newName = inputStream.readObject().toString();
				
					if (newName != null && UsernameManager.allocate(newName)) {
						UsernameManager.remove(username);
						server.changeUsername(username, newName);
						username = newName;
						outputStream.writeObject(Commands.RESPONSE_OK);
					} else {
						outputStream.writeObject(Commands.RESPONSE_DENIED);
					}
					outputStream.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	

	/**
	 * sends a message to the client
	 * 
	 * @param line
	 *            text of the message
	 */
	public void send(TextMessage msg) {
		try {
			synchronized (outputStream) {
				outputStream.writeObject(msg);
				outputStream.flush();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	

	private boolean waitForLogin  () {
		boolean access = false;
		try {
			if (Commands.COMMAND_LOGIN == inputStream.readInt()) {
				if (Server.PASSWORD.equals(inputStream.readObject().toString())) {
					outputStream.writeInt(Commands.RESPONSE_OK);
					outputStream.writeObject(username);
					access = true;
				} else {
					outputStream.writeInt(Commands.RESPONSE_DENIED);
				}
				outputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return access;
	}


}
