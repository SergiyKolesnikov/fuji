
/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
    private String color = "black";
	/**
	 * decides what to do with incoming messages
	 * 
	 * @param name
	 *            name of the client
	 * @param msg
	 *            received message
	 */
	private void handleIncomingMessage(String name, Object msg) {
		String text="";
		
		if (msg instanceof TextMessage)
			text = ((TextMessage) msg).getContent();

	    if(text.split(":")[0].equals("Color"))
	    	this.color =text.split(":")[1];
		else	
			original(name,new TextMessage((this.color.equals("black")?"":"Color: "+this.color+ " - ")+text));
	
	}


}
