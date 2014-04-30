import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {

	public void broadcast(TextMessage msg) {
		synchronized (connections) {
			History.instance.logTextMessage(msg);
		}
		
		original(msg);
	}

}
