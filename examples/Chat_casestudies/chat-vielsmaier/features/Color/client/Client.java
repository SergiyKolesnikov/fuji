package client; 

import java.util.Iterator;

import common.Message;
import common.ColoredMessage;

public  class  Client {
	private String color = "black";
	
	public void send(String text) {
		send(new ColoredMessage(getUsername(), text, color));
	}
	
	private void handleMessage(Message o) {
		original(o);
		if(o.getClass().equals(ColoredMessage.class)) {
			ColoredMessage m = (ColoredMessage)o;
			for (Iterator i = chatListeners.iterator(); i.hasNext();) {
				((ChatListener) i.next()).handleColoredMessage(m.getFrom(), m.getText(), m.getColor());
			}
		}
	}
	
	public void setColor(String i) {
		color = i;
	}
}
