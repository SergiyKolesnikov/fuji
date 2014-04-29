package client;
import encryption.*;

public class PluginManager {
	public PluginManager(Client client) {
		addMessageExtension(new NoEncryption());
	}
}