package client;

public class GUI extends JFrame implements ChatListener {	
	private void handleCommand(String command) {
		original(command);
		if(command.toLowerCase().matches("/msg .+ .+")) {
			String[] a = command.split(" ", 3);
			chatClient.sendPrivate(a[1], a[2]);
		}
	}
	
	public void handlePrivateMessage(String from, String text) {
		output("Private message from " + from + ": " + text + "\n");
	}
}