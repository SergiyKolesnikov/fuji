/**
 * TODO description
 */
public class Server {

	public void sendMessage(Message msg, String to) {
		synchronized (connections) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				if (connection.getUserName().equals(to)) {
					connection.send(msg);
					break;
				}
			}
		}
	}

}