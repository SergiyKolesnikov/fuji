package client;

/**
 * simple chat client
 */
public class Client implements Runnable {
	
	public void run() {
		new Console(this);
		original();
	}
}
