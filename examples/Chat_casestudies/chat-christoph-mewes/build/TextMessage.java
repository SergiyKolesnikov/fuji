

import java.io.Serializable;
import javax.swing.JTextPane;



/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public class TextMessage extends AbstractMessage implements Viewable, Serializable {
	private static final long serialVersionUID = -9161595018411902079L;

	protected String sender;
	protected String content;

	public TextMessage(String content) {
		setContent(content);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		((TextMessage) this).content = content;
	}

	public void render(JTextPane pane) {
		if (((TextMessage) this).content.equals("")) return;
		
		pane.setEditable(true);
		
		int len = pane.getDocument().getLength();
		pane.setCaretPosition(len);
		pane.replaceSelection(((TextMessage) this).sender + ": " + ((TextMessage) this).content + "\n");

		pane.setEditable(false);
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String userID) {
		sender = userID;
	}
}