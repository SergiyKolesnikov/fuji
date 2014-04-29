package server; 

import java.io.IOException; 
import java.net.ServerSocket; 
import java.net.Socket; 
import java.util.ArrayList; 
import java.util.List; 
import server.Client; 

import common.Logging; 

public   class  Server  extends Thread {
	
	protected ServerSocket mServer;

		
	protected List<Client> mClients;

	
	
	List<Client> clients() { return mClients; }

	
	
	public Server  () throws IOException
	{
		// Server starten
		mServer = new ServerSocket(4444);
		
		// Liste f�r Clients anlegen
		mClients = new ArrayList<Client>();
		
		print("Server gestartet auf Port 4444");
	
		mLog = new Logging();
		mLog.open("serverlog.txt");
	}

	
	
	// Wird vom Logging-Feature entsprechend erweitert.
	 private void  print__wrappee__Encryption  (String str) { System.out.println(str); }

	
	
	public void print(String str)
	{
		print__wrappee__Encryption(str);
		
		// Wenn der Server noch startet, findet bereits ein print() Aufruf statt, bevor das Objekt initialisiert ist.
		if( mLog != null ) mLog.log(str + "\n");
	}

	
	
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

	
	
	 private void  broadcast__wrappee__Encryption  (String aMsg) 
	{
		for( int i=0; i<mClients.size(); i++ ) mClients.get(i).write(aMsg);	
	}

	
	
	public void broadcast(String aMsg) 
	{
		broadcast__wrappee__Encryption(aMsg);
		mLog.log(aMsg);
	}

	
	
	public void removeClient(Client aClient)
	{
		mClients.remove(aClient);
		aClient = null;
	}

	
	public Boolean isUserNameAvailable(String aUserName)
	{
		for( int i=0; i<mClients.size(); i++ ) if( mClients.get(i).getUserName().equals(aUserName) ) return false;
		
		return true;
	}

	
	public Boolean sendPrivateMessage(String aRecv, String aSender, String aMsg)
	{
		for( int i=0; i<mClients.size(); i++ ) if( mClients.get(i).getUserName().equals(aRecv) ) 
			return mClients.get(i).write("MSG Private Nachricht von " + aSender + ": " + aMsg + "\r\n" );
		
		return false;
	}

	
	protected Logging mLog;


}
