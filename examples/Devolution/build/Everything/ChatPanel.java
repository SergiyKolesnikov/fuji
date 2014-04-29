package Everything;
import javax.swing.*;



public class ChatPanel extends JTextArea {
	public ChatPanel() {
		setLineWrap(true);
		setEditable(false);
	}

	public void addMessage(String sender, String msg) {
		append("\n" + sender + ": " + msg);
	}
}