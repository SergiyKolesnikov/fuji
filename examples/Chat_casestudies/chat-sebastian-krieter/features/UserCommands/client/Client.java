package client;

import common.Commands;
import common.TextMessage;

public class Client {
	
	private static final String
		USERNAME_CHANGE = "/nick",
		PRIVATE_MESSAGE = "/msg";
	
	private int curCommand = 0;
	private String[] curArgs = null;
	
	public static void sendMessage(String line) {
		if (line.startsWith("/")) {
			if (canSend()) {
				String[] pcmnd = line.split(" ", 3);
				String cmnd = pcmnd[0].toLowerCase();
				if (USERNAME_CHANGE.equals(cmnd)) {
					INSTANCE.changeUsername(pcmnd[1]);
				} else if (PRIVATE_MESSAGE.equals(cmnd)) {
					privateMessage(pcmnd[1], pcmnd[2]);
				} else {
					sendObject(toTextMessage(line));
				}
			}
		} else {
			original(line);
		}
		
	}
	
	protected void handleIncomingMessage(Object msg) {
		original(msg);
		if (msg != null && msg instanceof Integer) {
			if (curCommand == Commands.COMMAND_USERNAME && Commands.RESPONSE_OK == (Integer) msg) {
				setUsername(curArgs[0]);
			}
		}
	}
	
	private void changeUsername(String name) {
		try {
			outputStream.writeObject(Commands.COMMAND_USERNAME);
			outputStream.writeObject(name);
			outputStream.flush();
			curArgs = new String[]{name};
			curCommand = Commands.COMMAND_USERNAME;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void privateMessage(String receiver, String line) {
		TextMessage msg = toTextMessage("@" + receiver + ": " + line);
		msg.setReceiver(receiver);
		sendObject(msg);
	}

}
