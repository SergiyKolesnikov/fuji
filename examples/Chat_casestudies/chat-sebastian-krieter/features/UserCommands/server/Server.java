package server;

import common.TextMessage;

public class Server {
	
	public void changeUsername(String oldName, String newName) {
		synchronized (connections) {
			Connection con = connections.remove(oldName);
			connections.put(newName, con);
		}
		broadcast(new TextMessage(TextMessage.SERVER_MESSAGE, oldName + " changed name to " + newName));
	}
	
	public void broadcast(TextMessage msg) {
		if (msg.getReceiver() == null) {
			original(msg);
		} else {
			synchronized (connections) {
				Connection conR = connections.get(msg.getReceiver());
				Connection conS = connections.get(msg.getSender());
				if (conR != null) {
					conR.send(msg);
					if (conS != null) {
						conS.send(msg);
					}
				} else {
					if (conS != null) {
						conS.send(new TextMessage(TextMessage.SERVER_MESSAGE, "no such user"));
					}
				}
				
				
			}
		}
		
	}
}
