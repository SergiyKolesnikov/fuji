public class Client {
	private static void initInterface(Client client, String host, String port) {
		new Gui("Chat " + host + ":" + port, client);
	}
}