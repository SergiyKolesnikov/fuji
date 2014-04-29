public class ChatServer {

	private Backend backend;

	private void initBackend() {
		this.backend = new BackendStub();
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
		
		original(msg, handle);
	}

}
