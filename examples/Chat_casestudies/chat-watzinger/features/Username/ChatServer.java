import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class ChatServer {

	private HashMap<String, SocketHandler> usernames = new HashMap<String, SocketHandler>();
	private HashMap<SocketHandler, String> handlesToUsernames = new HashMap<SocketHandler, String>();

	public void processMessage(Message msg, SocketHandler handle) {

		if (msg instanceof UserNameMessage) {
			synchronized (usernames) {
				synchronized (handlesToUsernames) {

					if (!usernames.keySet().contains(msg.getSource())
							&& !msg.getSource().toLowerCase().equals("server")) {

						// remove old username
						if (handlesToUsernames.get(handle) != null) {
							usernames.remove(handlesToUsernames.get(handle));
						}

						usernames.put(msg.getSource(), handle);
						handlesToUsernames.put(handle, msg.getSource());
						debug.append("> Set username for "
								+ handle.socket.getInetAddress() + " to "
								+ msg.getSource());
					} else {
						debug.append("> Username not available!");
						handle.send(new ChatMessage("Server",
								"Username not available!"));
					}
				}
			}
		}
		
		original(msg, handle);

	}

	private void setSource(ChatMessage msg, SocketHandler handle, boolean info) {
		original(msg, handle, info);

		if (handlesToUsernames.get(handle) != null && !info)
			msg.setSource(handlesToUsernames.get(handle));
	}

	public void removeHandle(SocketHandler handle) {
		original(handle);
		usernames.remove(handlesToUsernames.get(handle));
		handlesToUsernames.remove(handle);
	}
}
