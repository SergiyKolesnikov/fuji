package server; 

import java.util.Iterator; 

import server.Connection; 

public  class  Server {
	

	private String[] badWords = new String[3];

	

	public Server() {
		badWords[0] = "test";
		badWords[1] = "evil";
		badWords[2] = "foobar";
	}

	

	public void broadcast(String text) {

		if (text.contains(badWords[0]) || text.contains(badWords[1])
				|| text.contains(badWords[2])) {
			synchronized (connections) {
				for (Iterator iterator = connections.iterator(); iterator
						.hasNext();) {
					Connection connection = (Connection) iterator.next();

					connection.send(text);

				}
			}
		}

	}


}
