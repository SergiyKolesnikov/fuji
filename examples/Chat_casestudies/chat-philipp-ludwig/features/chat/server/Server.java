package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server.Client;

public class Server extends Thread 
{
	protected ServerSocket mServer;	
	protected List<Client> mClients;
	
	List<Client> clients() { return mClients; }

	public Server() throws IOException
	{
		// Server starten
		mServer = new ServerSocket(4444);
		
		// Liste für Clients anlegen
		mClients = new ArrayList<Client>();
		
		print("Server gestartet auf Port 4444");
	}
	
	// Wird vom Logging-Feature entsprechend erweitert.
	public void print(String str) { System.out.println(str); }
	
	public void run()
	{
		while(true)
		{
			Socket socket;
			try 
			{
				socket = mServer.accept();
				Client cl = new Client(socket, this);
				mClients.add(cl);
				cl.start();

				// Nachricht an die Konsole
				print("Verbindung hergestellt durch " + socket.getInetAddress().toString());						
				
				// Nachricht an den Client
				cl.write("CONNECTED to server\r\n");				
				
			} catch (IOException e) 
			{
				e.printStackTrace();
			}		
		}
	}
	
	public void broadcast(String aMsg) 
	{
		for( int i=0; i<mClients.size(); i++ ) mClients.get(i).write(aMsg);	
	}
	
	public void removeClient(Client aClient)
	{
		mClients.remove(aClient);
		aClient = null;
	}
}
