package client; 

import java.util.Iterator;

import common.Message;
import common.PrivateMessage;
import common.TextMessage;

public  class  Client {
	public void sendPrivate(String to, String text) {
		send(new PrivateMessage(to, getUsername(), text));
	}
	
	private void handleMessage(Message o) {
		original(o);
		if(o.getClass().equals(PrivateMessage.class)) {
			PrivateMessage m = (PrivateMessage)o;
			for (Iterator i = chatListeners.iterator(); i.hasNext();) {
				((ChatListener) i.next()).handlePrivateMessage(m.getFrom(), m.getText());
			}
		}
	}
}
