package client;

public class Client {
	
	private void incomingAction(Message msg) {
		Rot13.decode(msg);
		original(msg);
	}
	
	private static TextMessage toTextMessage(String line) {
		TextMessage msg = new TextMessage(username, Rot13.encode(line));
		msg.setEncoded(true);
		return msg;
	}
}
