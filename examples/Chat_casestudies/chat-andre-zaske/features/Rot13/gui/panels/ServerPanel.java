package gui.panels;

import crypto.Rot13;

/**
 * TODO description
 */
public class ServerPanel {
	private void init() {
		original();
		knowCiphers.put("Rot13",Rot13.class.getName());
	}
}