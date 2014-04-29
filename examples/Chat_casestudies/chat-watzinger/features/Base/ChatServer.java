import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Daniel Watzinger
 */
public class ChatServer {

	private static final String ERROR_SOCKET = "Error while initializing socket! Exiting";
	private Set<SocketHandler> handles = new LinkedHashSet<SocketHandler>();
	private ServerSocket socket;
	private SocketHandlerFactory factory;
	private Debug debug;

	public ChatServer(int port, SocketHandlerFactory factory, Debug debug) {
		try {
			debug.append("Starting server...");
			debug.append("> Initialize socket");
			this.socket = new ServerSocket(port);
			this.factory = factory;
			this.debug = debug;
		} catch (IOException e) {
			debug.appendError(ERROR_SOCKET);
			debug.appendError(e.toString());
		}
	}
	
	private void initBackend() {
		
	}
	
	private void initHistory() {
		
	}

	@SuppressWarnings("unchecked")
	public void start() {
		initBackend();
		initHistory();

		while (true) {

			try {
				debug.append("Waiting for new connection...");
				SocketHandler handle = factory.newInstance(socket.accept(),
						this);
				debug.append("> New Connection from "
						+ handle.socket.getInetAddress());
				new Thread(handle).start();
				handles.add(handle);
				newConnection(handle);
			} catch (IOException e) {
				debug.appendError(e.toString());
			}
		}
	}

	private void newConnection(SocketHandler handle) {
		debug.append("> Started connection handle");
		ChatMessage info = new ChatMessage("Server",
				handle.socket.getInetAddress() + " joined.");
		broadcast(info, handle, true);
	}
	
	private boolean bcast(Message msg, SocketHandler handle) {
		return true;
	}

	public void processMessage(Message msg, SocketHandler handle) {

		if (msg instanceof ChatMessage && bcast(msg,handle)) {
			broadcast((ChatMessage) msg, handle, false);
		}
	}

	private void broadcast(ChatMessage msg, SocketHandler handle, boolean info) {
		setSource(msg, handle, info);

		synchronized (handles) {
			debug.append("Broadcast of: <" + msg + ">");

			for (SocketHandler h : handles) {
				h.send(msg);
			}
		}
	}

	private void setSource(ChatMessage msg, SocketHandler handle, boolean info) {
		msg.setSource(info ? "Server" : handle.socket.getInetAddress()
				.toString());
	}

	public void removeHandle(SocketHandler handle) {
		handles.remove(handle);
		ChatMessage info = new ChatMessage("Server",
				handle.socket.getInetAddress() + " left.");
		broadcast(info, handle, true);
		debug.append("> Removed connection handle for "
				+ handle.socket.getInetAddress());
	}

}
