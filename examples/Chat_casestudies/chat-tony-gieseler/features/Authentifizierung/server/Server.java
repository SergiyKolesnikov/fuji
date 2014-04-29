package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.Connection;

/**
 * TODO description
 */
public class Server {
	private String pass = "Passwort";
	
	private void start(int port) throws IOException {
		ServerSocket server = new ServerSocket(port);
		while (true) {
			System.out.println("Waiting for Connections...");
			Socket client = server.accept();
			System.out.println("Accepted from " + client.getInetAddress());
			Connection c = connectTo(client);
			
			String passC = "";
			byte[]buf = new byte[20];
			int len = c.inputStream.read(buf);
				
			for(int i = 0; i < len; i++)
				passC += (char)buf[i];
			
			if(pass.equals(passC))
				c.start();
		}
	}
}