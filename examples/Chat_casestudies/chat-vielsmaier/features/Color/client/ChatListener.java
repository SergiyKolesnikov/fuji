package client;

public interface ChatListener {
	public void handleColoredMessage(String from, String text, String color);
}
