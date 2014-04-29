package client;

import messageExtension.*;

public class PluginManager {
	public PluginManager(Client client) {
		addMessageExtension(new SpamFilter());
	}
}