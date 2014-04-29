



	/**
	 * Listener that gets informed every time when the chat client receives a new
	 * MessageObject
	 */
public interface ChatListener {
	
	/**
	 * New text message received by client.
	 * 
	 * @param msgo
	 *            the new message
	 */
	void newChatLine(MessageObject msgo);
}