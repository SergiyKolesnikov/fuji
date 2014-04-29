import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Daniel Watzinger
 */
public class ChatServer {

	private History history;

	private void initHistory() {
		try {
			this.history = new History(new File("server.log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processMessage(Message msg, SocketHandler handle) {
		original(msg, handle);
		debug.append("Logging message: "+msg.getClass().getSimpleName());
		history.append(msg);
	}
}
