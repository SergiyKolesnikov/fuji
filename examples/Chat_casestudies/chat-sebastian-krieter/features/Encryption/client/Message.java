package client;

public class Message {
	private boolean encoded;

	public Message(String username, TextMessage m) {
		encoded = m.isEncoded();
	}

	private Message(String content) {
		encoded = false;
	}
	
	public boolean isEncoded() {
		return encoded;
	}

	public void setEncoded(boolean encoded) {
		this.encoded = encoded;
	}
}