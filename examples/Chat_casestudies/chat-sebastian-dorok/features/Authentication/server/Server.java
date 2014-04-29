package server;

import java.util.HashMap;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {

	private HashMap<String, String> registeredUsers;

	private void init() {
		this.registeredUsers = new HashMap<String, String>();
		registeredUsers.put("Basti", "12345");
		registeredUsers.put("Test", "Hallo");
		registeredUsers.put("Private", "12345");
		original();
	}

	public boolean grantAccess(String user, String password) {
		if (this.registeredUsers.containsKey(user)) {
			if (this.registeredUsers.get(user).equals(password)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
