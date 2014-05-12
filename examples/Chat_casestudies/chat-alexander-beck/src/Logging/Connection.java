
/**
 * class for an individual connection to a client. allows to send messages to
 * this client and handles incoming messages.
 */
public class Connection extends Thread {
	
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
		Logging(text);
		original(name,new TextMessage(text));
      
		
	}
	private void Logging(String message){
		try{
			java.util.Date now = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yy HH.mm");

			FileWriter writer = new FileWriter("serverLog.txt",true);
			writer.write(sdf.format(now)+" - " + message+"\r\n");
			writer.close();
		}catch (IOException ef){
			System.out.println("Fehler beim schreiben der Datei");
		}
	}
}
