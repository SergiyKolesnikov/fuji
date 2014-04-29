package chat;

import chat.messages.AMessage;

public interface IClient {
	public void unicast(AMessage message);
	public void setAuthenticated(boolean authenticated);
	public boolean getAuthenticated();
}
