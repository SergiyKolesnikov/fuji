package client;

public class Client {
	
	private final Console console = new Console();

	private void newChatLine(Message msg) {
		console.newChatLine(msg);
		original(msg);
	}
}
