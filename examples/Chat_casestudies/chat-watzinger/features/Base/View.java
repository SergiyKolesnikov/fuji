import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class View implements Observer {
	protected ChatClient client;
	protected BlockingQueue<ChatMessage> queue = new LinkedBlockingDeque<ChatMessage>();

	public View(InetAddress serverAddress, int serverPort) {
		try {
			client = new ChatClient(serverAddress, serverPort,
					new StdOutDebug());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread t = new Thread(client);
		client.addObserver(this);
		t.start();
		init();
	}
	
	private void init() {
		
	}
	
	public String getUsername() {
		return "";
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {

		if (arg1 instanceof ChatMessage) {
			queue.add((ChatMessage) arg1);
		}
	}
}
