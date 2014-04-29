import java.util.HashMap;
import java.util.Map;

public class Connection {
	private boolean connectionAuthenticated = false;
	private static final Map<String, String> accounts = new HashMap<String, String>();
	static {
	  accounts.put("user1", "pw1");
	  accounts.put("user2", "pw2");
	  accounts.put("user3", "pw3");
	}
	
	private void handleIncomingMessage(String host, Object msg) {
		original(host, msg);
		
		if (msg instanceof AuthMessage) {
			handleAuthMessage(host, (AuthMessage) msg);
		}		
	}
	
	private void handleAuthMessage(String host, AuthMessage msg) {
		if (accounts.containsKey(msg.getUsername())) {
			String accountPassword = accounts.get(msg.getUsername());
			if (accountPassword.equals(msg.getPassword())) {
				send("Authenticated!");
				connectionAuthenticated = true;
			} else {
				send("Password wrong!");
			}
		} else {
			send ("No such username in database");
		}
	}
	
	private void handleTextMessage(String host, TextMessage msg) {
		if (connectionAuthenticated) {
			server.broadcast(host, msg);
		} else {
			send("Please authenticate yourself! \\auth user password");
		}
	}	
}