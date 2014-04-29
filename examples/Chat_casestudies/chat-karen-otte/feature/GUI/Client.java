

/**
 * TODO description
 */
public class Client implements Runnable {
	
	protected void init(String host, int port) {
		original(host, port);
		new Gui("Chat " + host + ":" + port, this);
	}

}