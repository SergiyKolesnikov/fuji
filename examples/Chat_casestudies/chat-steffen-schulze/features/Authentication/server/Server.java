package server;

import common.Users;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {

	public boolean checkLogin(String user, String password) {
		return Users.checkLogin(user, password);
	}

}
