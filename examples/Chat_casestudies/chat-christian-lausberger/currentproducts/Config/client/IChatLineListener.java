package client; 

/**
 * Listener that gets informed every time when the chat client receives a new
 * message
 */
public  interface  IChatLineListener {
	
	/**
	 * New text message received by client.
	 * 
	 * @param line
	 *            the new message
	 */
	void newChatLine(String line);

	
	void stop();


}
