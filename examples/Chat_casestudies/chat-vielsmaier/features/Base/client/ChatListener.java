package client;

import common.JoinMessage;
import common.TextMessage;

public interface ChatListener {
	public void handleLine(String from, String text);
	public void handleJoin(String username);
}
