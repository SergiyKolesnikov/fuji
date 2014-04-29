package server;

import java.util.HashMap;
import java.util.Map;

import common.AuthFailedMessage;
import common.AuthMessage;
import common.AuthOKMessage;
import common.JoinMessage;
import common.Message;

public class Connection {
	private static Map authenticatedUsers;
	
	private static Map getAuthenticatedUsers() {
		if(authenticatedUsers == null) {
			authenticatedUsers = new HashMap();
			authenticatedUsers.put("moser", "foo");
		}
		return authenticatedUsers;
	}
	
	private boolean authenticated = false;

	private boolean checkAuth(AuthMessage o) {
		return getAuthenticatedUsers().containsKey(o.getUsername()) && getAuthenticatedUsers().get(o.getUsername()).equals(o.getPassword());
	}
	
	private void handleMessage(Message o) {
		if(authenticated)
			original(o);
		if(o instanceof AuthMessage && !authenticated) { //ignore multiple authmessages
			authenticated = checkAuth((AuthMessage)o);
			if(!authenticated) {
				send(new AuthFailedMessage());
			} else {
				send(new AuthOKMessage());
				username = ((AuthMessage)o).getUsername();
				server.broadcast(new JoinMessage(username));
			}
		}
	}
}