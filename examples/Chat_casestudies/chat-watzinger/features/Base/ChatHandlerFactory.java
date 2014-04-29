import java.net.Socket;

public class ChatHandlerFactory implements SocketHandlerFactory {

	@Override
	public SocketHandler newInstance(Socket socket, ChatServer server) {
		return new ObjectStreamSocketHandler(socket, server);
	}

}
