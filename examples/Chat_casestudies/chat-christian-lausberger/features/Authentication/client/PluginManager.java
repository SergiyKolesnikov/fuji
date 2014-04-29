package client;

public class PluginManager {
	
	public PluginManager(Client client) {
		onConnect.add(new client_authentication());
	}
}