package client;


/**
 * simple swing gui for the chat client
 */
public class GUI extends JFrame implements ChatListener {
	public void requestLogin() {
        new LoginDialog(chatClient);
	}
}