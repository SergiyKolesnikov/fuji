package client;

public class Client {
	
	private void incomingAction(Message msg) {
		SWL.decode(msg);
		original(msg);
	}
	
	private static TextMessage toTextMessage(String line) {
		TextMessage msg = new TextMessage(username, SWL.encode(line));
		msg.setEncoded(true);
		return msg;
	}
}
