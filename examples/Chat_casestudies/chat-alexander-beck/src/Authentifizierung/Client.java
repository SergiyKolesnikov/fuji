/**
 * simple chat client
 */
public class Client implements Runnable {

    protected boolean isAuthenticated=false;

	
	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		if (msg instanceof TextMessage) {
			if (!isAuthenticated&&((TextMessage) msg).getContent().equals("Sie sind am Chat angemeldet"))
				isAuthenticated=true;
			original(msg);
		}
	}
	public void send(String line) {
		if(!isAuthenticated)
			original("Passwort:12345");
		original(line);
		
	}
	
	
	

}
