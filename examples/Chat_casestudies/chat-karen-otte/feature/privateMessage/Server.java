import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * TODO description
 */
public class Server {
	
	public Map<String, Connection> privateConnections;
	
	
	public void privateMessage(TextMessage txt){
		boolean sendet = false;
		if (txt.isPrivate){
			synchronized (connections) {
				for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
					Connection connection = (Connection) iterator.next();
					if (connection.hashCode() == privateConnections.get(txt.receiverName).hashCode()){
						connection.send("Private Message von" + txt.sender + ": "+ txt.getMessage());
						sendet = true;
					}
				}
			}			
		}
		if (!sendet){
			System.out.println("Empfaenger unbekannt.");
		}
	}
	
	public void addPrivateConnection(String name, Connection con){
		if (privateConnections == null){
			privateConnections = new HashMap<String, Connection>();
		}
		privateConnections.put(name, con);
	}

}