package client;

public class GUI extends JFrame implements ChatListener {	
	private void handleCommand(String command) {
		original(command);
		if(command.toLowerCase().matches("/setusername .+")) {
			String[] a = command.split(" ", 2);
			chatClient.setUsername(a[1].replace(" ", ""));
		}
	}
	
	public void handleUsernameChange(String oldName, String newName) {
		output(oldName + " changed his name to " + newName + "\n");
	}
}