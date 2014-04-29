package server;

import java.util.HashSet;

/**
 * server's main class. accepts incoming connections and allows broadcasting
 */
public class Server {

	private HashSet<String> spamList;

	private void init() {
		this.spamList = new HashSet<String>();
		this.spamList.add("Spam");
		this.spamList.add("Fuck");
		original();
	}

	public boolean doesMessageContainSpam(String msg) {
		for (String word : spamList) {
			if (msg.contains(word)) {
				return true;
			}
		}
		return false;
	}

}
