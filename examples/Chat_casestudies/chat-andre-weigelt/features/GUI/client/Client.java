package client;

/**
 * simple chat client
 */
public class Client implements Runnable {

	public Client(String host, int port) {
		new Gui(this);
	}
}
