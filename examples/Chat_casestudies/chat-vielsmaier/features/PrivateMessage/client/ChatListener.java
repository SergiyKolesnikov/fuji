package client;

public interface ChatListener {
	public void handlePrivateMessage(String from, String text);
}
