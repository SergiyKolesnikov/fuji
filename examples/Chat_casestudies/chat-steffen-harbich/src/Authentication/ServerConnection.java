import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServerConnection {

	private static Map<String, String> userNameToPassphraseMap;
	private static Set<String> connectedUsers = new HashSet<String>();
	
	static {
		userNameToPassphraseMap = new HashMap<String, String>();
		userNameToPassphraseMap.put("Test1", "Bingo");
		userNameToPassphraseMap.put("Test2", "Password");
	}
	
	protected void runCommunicationLoop() {
		connectionEstablished();
		original();
		connectionClosed();
	}

	private boolean isAuthorized(AuthenticationMessage msg) {
		if (userNameToPassphraseMap.containsKey(msg.getUserName()) == false)
			return false;
		
		if (userNameToPassphraseMap.get(msg.getUserName()).equals(msg.getPassPhrase()) == false)
			return false;
		
		if (connectedUsers.contains(msg.getUserName()) == true)
			return false;
		
		return true;
	}

	private synchronized void connectionEstablished() {
		// at the server we wait for a message from client that contains
		// authentication information
		
		Message msg = receive();
		
		if (msg instanceof AuthenticationMessage) {
			AuthenticationMessage authMsg = (AuthenticationMessage) msg;
			
			if (isAuthorized(authMsg)) {
				connectedUsers.add(authMsg.getUserName());
				setUserName(authMsg.getUserName());
				send(new AuthenticationResultMessage(true));
			}
			else {
				send(new AuthenticationResultMessage(false));
				close();
			}
		}
		else if (msg != null) {
			send(MessageFactory.newTextMessage("Error: authentication required!"));
			close();
		}
	}
	
	private synchronized void connectionClosed() {
		connectedUsers.remove(getUserName());
	}

}
