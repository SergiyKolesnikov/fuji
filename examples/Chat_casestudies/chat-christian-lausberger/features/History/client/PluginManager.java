package client;

import client.Client;
import output.*;

public class PluginManager {
	public PluginManager(Client client) {
		addLineListener(new History(client.getUser()));
	}
}