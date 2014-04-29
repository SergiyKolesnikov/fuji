package server;

import java.io.IOException;

import common.ROT13;

/**
 * TODO description
 */
public class Server {
	private void start(int port) throws IOException {
		ROT13 v = new ROT13();
		pass = v.crypt(pass);
		
		original(port);
	}
}