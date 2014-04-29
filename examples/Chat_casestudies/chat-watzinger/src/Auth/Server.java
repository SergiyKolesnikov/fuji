import java.util.Arrays; 
import java.util.HashSet; 
import java.util.Set; 

public  class  Server {
	
	
	private static final String ERROR_ARGUMENT = "Command-Line arguments missing or of wrong format! Exiting";

	
	private static final String ERROR_SOCKET = "Error while initializing socket! Exiting";

	
	private static final String ERROR_FILE_ACCESS = "Error while accessing file! Exiting";

	
	private static final int DEFAULT_PORT = 8000;

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			int port = args.length > 0 ? Integer.parseInt(args[0])
					: DEFAULT_PORT;
			ChatServer server = new ChatServer(port, new ChatHandlerFactory(),
					new StdOutDebug());
			server.start();
		} catch (NumberFormatException e) {
			System.err.println(ERROR_ARGUMENT + "\n" + e);
			e.printStackTrace();
		}
	}


}
