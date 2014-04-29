package common;

public class TextMessage extends Message {
	public String textForLog() {
		return getFrom() + ": " + getText() + "\n";
	}
}
