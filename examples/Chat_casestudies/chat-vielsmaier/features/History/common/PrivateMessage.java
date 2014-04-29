package common;

public class PrivateMessage extends TextMessage {
	public String textForLog() {
		return "Private message from " + getFrom() + ": " + getText() + "\n";
	}
}