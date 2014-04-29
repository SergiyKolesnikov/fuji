package interfaces;

public interface IClient extends ChatLineListener {	
	/**
	 * New text message received by client.
	 * 
	 * @param line
	 *            the new message
	 */
	@Override
	public void newChatLine(String line);
	
	public void start();
	
	public void stop();
	
	public void setClientProxy(IClientProxy proxy);
}
