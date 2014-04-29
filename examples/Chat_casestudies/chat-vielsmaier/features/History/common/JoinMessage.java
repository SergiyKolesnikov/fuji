package common;

public class JoinMessage extends Message {
	public String textForLog() {
		return getUsername() + " joined\n";
	}
}
