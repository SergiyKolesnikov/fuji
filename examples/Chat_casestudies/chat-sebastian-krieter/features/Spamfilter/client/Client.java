package client;

public class Client implements Runnable {
	
	private void incomingAction(Message msg) {
		SpamFilter.filter(msg);
		original(msg);
	}
}
