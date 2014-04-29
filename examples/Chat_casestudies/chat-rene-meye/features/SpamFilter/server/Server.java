package server;

import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO description
 */
public class Server {
	public ArrayList<String> blacklist;

	private void initStuff() {
		blacklist = new ArrayList<String>();
		blacklist.add("Arsch");
		blacklist.add("Spam");
		blacklist.add("Microsoft");
		blacklist.add("CDU");
		
		original();
	}
}

