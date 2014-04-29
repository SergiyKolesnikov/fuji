public class ChatServer implements Runnable {
	
	public void pn(String reciever, String msg) {
		for (int i = 0; i < connections.size(); i++) {
			Connection currentConnection = connections.elementAt(i);
			if (currentConnection.getClientName().equals(reciever)) {
				sendMsgTo(currentConnection, currentConnection.getClientName(), msg);
			}
		}
	}
}