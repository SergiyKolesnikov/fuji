package client;

import common.ROT13;

/**
 * TODO description
 */
public class Client {
	private void start(String host, int port) {
		ROT13 v = new ROT13();
		pass = v.crypt(pass);
		
		original(host, port);
	}
}