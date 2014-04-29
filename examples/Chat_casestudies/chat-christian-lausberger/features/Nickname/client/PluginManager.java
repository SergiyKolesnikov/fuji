package client;

import common.TextMessage;

public class PluginManager {
	public PluginManager  (Client client) {
		addMessageExtension(new SenderExtension());
	}
	
	public TextMessage send(String reciever, String line){
		TextMessage msg = new TextMessage(line);
		msg.setReciever(reciever);
		for (IMessageExtension me : messageExtensionListeners) {
			msg = me.extendMessage(msg, client);
		}
		return msg;
	}
	public void incomingMessage(TextMessage msg){
		if (msg.privatMessage){
			if (msg.getReciever().equalsIgnoreCase(client.user)){
				original(msg);
			}
		}else original(msg);
	}
}