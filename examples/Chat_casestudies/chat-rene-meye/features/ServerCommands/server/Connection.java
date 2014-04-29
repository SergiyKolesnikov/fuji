package server;

import common.ServerCommandMessage;

/**
 * TODO description
 */
public class Connection {
	private void handleIncomingMessage(String name, Object msg) {
		if (msg instanceof ServerCommandMessage) {
			ServerCommandMessage commandMsg = (ServerCommandMessage) msg;
			System.out.println("Got Server Command: "+commandMsg.getCommand()+ " ("+commandMsg.getOptions()+")");
		}
		original(name, msg);
	}
}