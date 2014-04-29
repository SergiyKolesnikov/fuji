package server;

import java.io.IOException;

import server.Server;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{		
		Server srv;		
		try
		{
			srv = new Server();
		}
		catch(IOException e)
		{
			System.out.println("Fehler: Der Server konnte nicht gestartet werden. Ist der Port 4444 bereits belegt?\n");
			return;
		}
		srv.start();
	}
}