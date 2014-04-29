package client.gui;

import client.ClientObserver;
import common.message.StatusMessage;
import common.message.TextMessage;

public class ConsoleOutput implements ClientObserver {

	/**
	 * this method gets called every time a new message is received (observer
	 * pattern)
	 */
	public void printNewChatLine(TextMessage msg) {
		TextMessage textmsg = (TextMessage) msg;
		System.out.println(textmsg.getFrom() + ": " + textmsg.getContent());
	}

	@Override
	public void printStatusMessage(StatusMessage msg) {
		StatusMessage statmsg = (StatusMessage) msg;
		System.out.println(statmsg.getStatus() + ": " + statmsg.getReason());
	}
}
