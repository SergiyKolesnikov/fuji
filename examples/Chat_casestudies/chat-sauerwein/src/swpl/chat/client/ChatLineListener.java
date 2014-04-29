package swpl.chat.client;
import swpl.chat.common.TextMessage;



/**
 * Listener that gets informed every time when the chat client receives a new
 * message
 */
public interface ChatLineListener {
	/**
	 * New text message received by client.
	 * 
	 * @param line
	 *            the new message
	 */
	void newChatLine(TextMessage textMsg);
}