package client; 

import common.Message; 
/**
 * Listener that gets informed every time when the chat client receives a new
 * message
 */
public  interface  ChatObserver {
	
	/**
	 * New message received by client.
	 * 
	 * @param msg
	 *            the new message
	 */
	public void newMessage(Object msg);

	
	/**
	 * 
	 * 
	 * @param msg
	 *            the new message
	 */
	public void loadPlugin(ChatPlugin p);

	
	
	public void removePlugin(ChatPlugin p);

	
	
	public Message notifyPlugins(Message msg);


}
