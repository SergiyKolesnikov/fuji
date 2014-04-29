package chat; 

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 

import chat.client.gui.graphical.Client; 
import chat.server.Server; 

public  class  Chat {
	

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out
				.println("Which part of the chat application should be started: server|client|console ?");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String command = reader.readLine().toLowerCase();
		if (command.equals("server")) {
			Server.main(args);
		} else if (command.equals("client")) {
			Client.main(args);
		}
	}


}
