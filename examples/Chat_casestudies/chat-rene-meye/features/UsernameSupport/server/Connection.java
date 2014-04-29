package server;

import common.ServerCommandMessage;

/**
 * TODO description
 */
public class Connection {
	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof ServerCommandMessage && ((ServerCommandMessage)msg).getCommand().equalsIgnoreCase("username")) {
			ServerCommandMessage commandMsg = (ServerCommandMessage) msg;
			if(this.user != null ){
				this.user.realName = commandMsg.getOptions();
			}
		}
		original(name, msg);
	}
}