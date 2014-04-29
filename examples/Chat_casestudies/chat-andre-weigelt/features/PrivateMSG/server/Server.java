package server;

import java.util.HashSet;
import java.util.Iterator;

import common.TextMessage;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {

	/**
	 * list of all known connections
	 */
	@SuppressWarnings("rawtypes")
	protected HashSet connections = new HashSet();

	public void broadcast(TextMessage msg) {
		if (msg.getEmpfaenger() != null && !msg.getEmpfaenger().equals("")) {
			for (Iterator iterator = connections.iterator(); iterator.hasNext();) {
				Connection connection = (Connection) iterator.next();
				if (connection.getMyName() != null
						&& connection.getMyName().equals(msg.getEmpfaenger())) {
					connection.send(msg);
				}

			}
		} else {
			original(msg);
		}

	}

}
