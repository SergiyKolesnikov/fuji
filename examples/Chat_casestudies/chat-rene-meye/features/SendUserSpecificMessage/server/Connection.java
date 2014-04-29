package server;

import common.ServerCommandMessage;
import common.TextMessage;

/**
 * TODO description
 */
public class Connection {
	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof ServerCommandMessage && ((ServerCommandMessage)msg).getCommand().equalsIgnoreCase("msg")) {
			ServerCommandMessage commandMsg = (ServerCommandMessage) msg;
			
			//TODO: Hier kšnnte man mal darauf achten, dass auch User mit Space im Namen mšglich werden.
			int spacePosition = commandMsg.getOptions().indexOf(" ");
			if(spacePosition >= 1){
				String realName = commandMsg.getOptions().substring(0, spacePosition);
				String message = commandMsg.getOptions().substring(spacePosition);
				server.sendToRealName(realName, name + " - " + message);
			}
		}
		original(name, msg);
	}
}