import java.net.Socket;

public class ClientConnection {

	private void handleIncomingMessage(Message msg) {
		if (msg instanceof TextMessage) {
			History.instance.logTextMessage((TextMessage)msg);
		}
		original(msg);
	}

}
