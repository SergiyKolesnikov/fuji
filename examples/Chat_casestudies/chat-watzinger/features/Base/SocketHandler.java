import java.net.Socket;

public abstract class SocketHandler implements Runnable {
	protected Socket socket;
	protected ChatServer server;

	public SocketHandler(Socket socket, ChatServer server) {
		this.socket = socket;
		this.server = server;
	}

	public abstract void send(Message msg);
}
