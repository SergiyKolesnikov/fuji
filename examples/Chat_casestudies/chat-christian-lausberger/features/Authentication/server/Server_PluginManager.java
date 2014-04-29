package server;

/**
 * TODO description
 */
public class Server_PluginManager {
	public Server_PluginManager() {
		onConnect.add(new server_authentication());
	}
}