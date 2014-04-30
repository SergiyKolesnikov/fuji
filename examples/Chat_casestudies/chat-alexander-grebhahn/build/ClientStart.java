
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;





abstract class ClientStart$$Base {


	static Client client; 
	
	public static void main(String args[]) throws IOException {
		if (args.length != 3)
			throw new RuntimeException("Syntax: ChatClient <host> <port> <name>");

		
		client = new Client(args[0], Integer.parseInt(args[1]));	
		
		client.name = args[2];
		

	}
}



public class ClientStart extends  ClientStart$$Base  {

	public static void main(String args[]) throws IOException {
		ClientStart$$Base.main(args);	
		
		
		client.myConsole = new Console(client);
		
	}
}