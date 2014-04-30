public class Client {
	
	public void startUI(ClientConnection client) {
		original(client);
		new Gui("Chat Client", client);
	}

}
