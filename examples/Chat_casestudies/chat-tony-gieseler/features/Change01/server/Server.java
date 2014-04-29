package server;

import java.io.IOException;
import common.Change01;


/**
 * TODO description
 */
public class Server {
	private void start(int port) throws IOException {
		Change01 v = new Change01();
		pass = v.crypt(pass);
		
		original(port);
	}
}