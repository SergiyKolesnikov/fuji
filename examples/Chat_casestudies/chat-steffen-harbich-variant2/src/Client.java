import java.io.IOException; 
import java.net.Socket; public   class  Client {
	

	public Client(String host, int port) throws IOException {
		System.out.println("Connecting to " + host + " (port " + port + ")...");
		Socket s = new Socket(host, port);
		
		ClientConnection client = new ClientConnection(s);
		client.start();
		
		// start the client interface
		startUI(client);
	}

	
	
	public void  startUI__wrappee__Server  (ClientConnection client) {
		
	}

	
	
	public void startUI(ClientConnection client) {
		startUI__wrappee__Server(client);
		new Console(client);
	}


}
