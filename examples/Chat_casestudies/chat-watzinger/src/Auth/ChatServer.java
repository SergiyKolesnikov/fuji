import java.awt.Color; 
import java.io.IOException; 
import java.net.ServerSocket; 
import java.util.LinkedHashSet; 
import java.util.Set; 
import java.util.HashMap; public   class  ChatServer {
	

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

	

	private void initBackend  () {
		this.backend = new BackendStub();
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

	

	 private void  processMessage__wrappee__Base  (Message msg, SocketHandler handle) {

		if (msg instanceof ChatMessage && bcast(msg,handle)) {
			broadcast((ChatMessage) msg, handle, false);
		}
	}

	

	 private void  processMessage__wrappee__Username  (Message msg, SocketHandler handle) {

		if (msg instanceof UserNameMessage) {
			synchronized (usernames) {
				synchronized (handlesToUsernames) {

					if (!usernames.keySet().contains(msg.getSource())
							&& !msg.getSource().toLowerCase().equals("server")) {

						// remove old username
						if (handlesToUsernames.get(handle) != null) {
							usernames.remove(handlesToUsernames.get(handle));
						}

						usernames.put(msg.getSource(), handle);
						handlesToUsernames.put(handle, msg.getSource());
						debug.append("> Set username for "
								+ handle.socket.getInetAddress() + " to "
								+ msg.getSource());
					} else {
						debug.append("> Username not available!");
						handle.send(new ChatMessage("Server",
								"Username not available!"));
					}
				}
			}
		}
		
		processMessage__wrappee__Base(msg, handle);

	}

	

	public void processMessage(Message msg, SocketHandler handle) {
		if (msg instanceof AuthMessage && !handle.isAuthenticated()) {
			AuthMessage m = (AuthMessage) msg;
			handle.setAuthenticated(backend.authenticate(m.getSource(),
					m.getPassword()));
			debug.append("> Authentication status: " + handle.isAuthenticated()
					+ " for " + handle.socket.getInetAddress());
			handle.send(new ChatMessage("Server", "Authentication status: "
					+ handle.isAuthenticated()));
		}
		
		processMessage__wrappee__Username(msg, handle);
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

	

	 private void  setSource__wrappee__Base  (ChatMessage msg, SocketHandler handle, boolean info) {
		msg.setSource(info ? "Server" : handle.socket.getInetAddress()
				.toString());
	}

	

	private void setSource(ChatMessage msg, SocketHandler handle, boolean info) {
		setSource__wrappee__Base(msg, handle, info);

		if (handlesToUsernames.get(handle) != null && !info)
			msg.setSource(handlesToUsernames.get(handle));
	}

	

	 private void  removeHandle__wrappee__Base  (SocketHandler handle) {
		handles.remove(handle);
		ChatMessage info = new ChatMessage("Server",
				handle.socket.getInetAddress() + " left.");
		broadcast(info, handle, true);
		debug.append("> Removed connection handle for "
				+ handle.socket.getInetAddress());
	}

	

	public void removeHandle(SocketHandler handle) {
		removeHandle__wrappee__Base(handle);
		usernames.remove(handlesToUsernames.get(handle));
		handlesToUsernames.remove(handle);
	}

	

	private HashMap<String, SocketHandler> usernames = new HashMap<String, SocketHandler>();

	
	private HashMap<SocketHandler, String> handlesToUsernames = new HashMap<SocketHandler, String>();

	

	private Backend backend;


}
