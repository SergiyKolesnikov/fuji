import java.util.Iterator;

public class Server {
	public void sendPrivateMessage(Connection sender, String recipient, TextMessage msg) {
		String senderName = sender.getUsername();
		
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				if (connection.getUsername().equals(recipient)) {
					msg.setContent("private msg (" + senderName + " -> " + recipient + ") : " + msg.getContent());
					connection.send(msg);
					sender.send(msg);
					return;
				}
			}
			sender.send("Username " + recipient + " not registered!");
		}
	}
}