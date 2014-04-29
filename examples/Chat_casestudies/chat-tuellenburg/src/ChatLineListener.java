

public  interface ChatLineListener {
	/**
	 * New text message received by client.
	 * 
	 * @param line
	 *            the new message
	 */
	void newChatLine(String line);
}