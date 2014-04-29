package client;

import client.Client;
import clientPlugIns.ConsoleClient;

/**
 * simple chat client
 */
public class Client implements Runnable, IClientProxy {
	
	public static void main(String args[]) throws IOException {
		
		original(args);
		
		Client client = new Client(args[0], Integer.parseInt(args[1]));
		//add the Console Client
		client.addClient(new ConsoleClient());
	}
}
