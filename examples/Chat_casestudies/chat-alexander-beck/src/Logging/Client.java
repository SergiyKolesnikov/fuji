

/**
 * simple chat client
 */
public class Client implements Runnable {


	/**
	 * decides what to do with incoming messages
	 * 
	 * @param msg
	 *            the message (Object) received from the sockets
	 */
	protected void handleIncomingMessage(Object msg) {
		Logging(((TextMessage) msg).getContent());
		original(msg);
	}

	private void Logging(String message){
		try{
			java.util.Date now = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yy HH.mm");

			FileWriter writer = new FileWriter("clientLog.txt",true);
			writer.write(sdf.format(now)+" - " + message+"\r\n");
			writer.close();
		}catch (IOException ef){
			System.out.println("Fehler beim schreiben der Datei");
		}
	}
	

}
