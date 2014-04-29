package server;

import common.Message;
import common.PrivateMessage;

public class Connection {	
	private void handleMessage(Message o) {
		if(o instanceof PrivateMessage) {
			server.sendTo(((PrivateMessage)o).getTo(), o);
		} else {
			original(o);
		}
	}
	
	public String getUsername() {
		return username;
	}
}