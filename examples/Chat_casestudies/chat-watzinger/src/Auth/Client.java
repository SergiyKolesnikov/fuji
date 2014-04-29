import java.net.InetAddress; 
import java.net.UnknownHostException; 


public  class  Client {
	
	private static final int DEFAULT_PORT = 8000;

	
	private static final String DEFAULT_HOST = "localhost";

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String host = DEFAULT_HOST;
		int port = DEFAULT_PORT;

		if (args.length == 2) {
			host = args[0];
			port = Integer.parseInt(args[1]);
		}

		try {	
			new GraphicalUI(InetAddress.getByName(host), port);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}


}
