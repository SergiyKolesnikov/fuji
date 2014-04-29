import java.util.HashMap;

public class ChatServer {

	private boolean bcast(Message msg, SocketHandler handle) {
		return !(msg instanceof PrivateMessage);
	}

	public void processMessage(Message msg, SocketHandler handle) {
		if (msg instanceof PrivateMessage) {
			PrivateMessage m = (PrivateMessage) msg;
			SocketHandler recipient = usernames.get(m.getTarget());
			
			if (recipient != null) {
				setSource(m, handle, false);
				recipient.send(msg);
			} else {
				handle.send(new ChatMessage("Server", m.getTarget()+" is not online!"));
			}
		}
		
		original(msg, handle);
	}

}
