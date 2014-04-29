package client; 

import javax.swing.text.BadLocationException; 

/**
 * Listener that gets informed every time when the chat client receives a new
 * message
 */
public  interface  ChatLineListener {
	
	/**
	 * New text message received by client.
	 * 
	 * @param line
	 *            the new message
	 * @throws BadLocationException 
	 */
	void newChatLine(String line) throws BadLocationException;


}
