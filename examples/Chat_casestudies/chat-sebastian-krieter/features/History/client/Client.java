package client;

public class Client {
	
	private static final History history = new History();
	
	public static void close() {
		history.close();
		original();
	}
	
	private void setUsername(String name) {
		history.setUsername(name);
		original(name);
	}
	
	private void newChatLine(Message msg) {
		history.newChatLine(msg);
		original(msg);
	}
}
