package client;

public class Client {
	
	private final Gui gui = new Gui();
	
	private void setUsername(String name) {
		gui.setUsername(name);
		original(name);
	}
	
	private void newChatLine(Message msg) {
		gui.newChatLine(msg);
		original(msg);
	}
}