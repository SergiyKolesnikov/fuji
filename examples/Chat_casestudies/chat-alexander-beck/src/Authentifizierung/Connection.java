
/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	private boolean isAuthenticated=false;
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
		if (!isAuthenticated){
			isAuthenticated = Authentifizierung(text);
		}
		else{
		    original(name,new TextMessage(text));
		}
		        
		
	}
	
	
	private boolean Authentifizierung(String passwort){
		try{
			isAuthenticated = passwort.split(":")[1].equals("12345");
			if(isAuthenticated){
				System.out.println("Client hat sich erfolgreich authentifiziert");
				send("Sie sind am Chat angemeldet");
				return true;
			}else{
				System.out.println("Clientanmeldung wurde abgewiesen");
				send("Authentifizierung fehlgeschlagen");
				return false;
			}
		}catch (Exception ex) {
			System.out.println("Fehler bei der Authentifizierung des Clients");
		    return false;
		}
	}

}
