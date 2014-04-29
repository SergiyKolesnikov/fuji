package server;

import common.Message;
import common.SetUsernameMessage;

public class Connection {	
	private void handleMessage(Message o) {
		original(o);
		if(o instanceof SetUsernameMessage) {
			SetUsernameMessage incoming = (SetUsernameMessage) o;
			SetUsernameMessage outgoing = new SetUsernameMessage(username, incoming.getNewName());
			username = incoming.getNewName();
			server.broadcast(outgoing);
		}
	}
}