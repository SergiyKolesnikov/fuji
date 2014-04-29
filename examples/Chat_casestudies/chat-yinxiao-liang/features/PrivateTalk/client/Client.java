package client;

import common.TextMessage;

/**
 * TODO description
 */
public class Client {
	public void send(TextMessage msg) {
		msg.chatUser = login.StringUsername;
		msg.chatToUser = gui.touser;
		if(gui.whisper.equals("public"))
			msg.whisper = false;
		else
			msg.whisper = true;
		original(msg);
	}
}