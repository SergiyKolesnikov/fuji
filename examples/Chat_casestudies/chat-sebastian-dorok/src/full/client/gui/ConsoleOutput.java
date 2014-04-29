package client.gui; 

import client.ClientObserver; 
import common.message.StatusMessage; 
import common.message.TextMessage; 
import common.message.TextColor; 

public   class  ConsoleOutput  implements ClientObserver {
	

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void printNewChatLine  (TextMessage msg) {
		TextMessage textmsg = (TextMessage) msg;
		System.out.println(textmsg.getFrom() + ": " + textmsg.getContent()
				+ " (" + textmsg.getTextColor() + ")");
	}

	

	@Override
	public void printStatusMessage(StatusMessage msg) {
		StatusMessage statmsg = (StatusMessage) msg;
		TextColor textcolor = TextColor.RED;
		switch (statmsg.getStatus()) {
		case AUTH_FAIL:
		case SERVER_WARN:
		case CONNECT_FAIL:
			textcolor = TextColor.RED;
			break;
		case CONNECT_SUCC:
		case SERVER_INFO:
		case AUTH_SUCC:
			textcolor = TextColor.GREEN;
			break;
		case USER_JOINED:
		case USER_LEFT:
			textcolor = TextColor.BLUE;
			break;
		}
		System.out.println(statmsg.getStatus() + ": " + statmsg.getReason()
				+ " (" + textcolor + ")");
	}


}
