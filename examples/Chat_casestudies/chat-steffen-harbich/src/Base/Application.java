import java.io.IOException;

public class Application {
	
	public static String[] args;

	public static void main(String[] args) {
		Application.args = args;
		
		if (args.length <= 0)
			throw new RuntimeException("Specify \"server\" or \"client\" as first argument to start application!");

		if (args[0].equals("server"))
			startServer(args);
		else if (args[0].equals("client"))
			startClient(args);
		else
			throw new RuntimeException("Specify \"server\" or \"client\" as first argument to start application!");
	}
	
	public static void startServer(String[] args) {
		if (args.length != 2)
			throw new RuntimeException("Syntax: ChatServer <port>");
		
		try {
			new Server(Integer.parseInt(args[1]));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void startClient(String[] args) {
		if (args.length != 5)
			throw new RuntimeException("Syntax: ChatClient <host> <port> <user name> <passphrase>");

		try {
			new Client(args[1], Integer.parseInt(args[2]));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
