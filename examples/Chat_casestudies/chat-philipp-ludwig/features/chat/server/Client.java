package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import server.Server;

public class Client extends Thread
{
	private Socket mSocket;
	private BufferedReader in;
	private DataOutputStream out;
	private Server mServer;
	
	Client(Socket aSocket, Server aServer) throws IOException 
	{
		mSocket = aSocket;
		mServer = aServer;	
		
		in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		out = new DataOutputStream(mSocket.getOutputStream());
		
		write("SERVERMSG Hallo " + mSocket.getInetAddress().toString() + "!\r\n");
		
		// Nachricht an den Chat über den neuen Teilnehmer
		String brd = "CHANNELMSG ";
		brd += mSocket.getInetAddress().toString();
		brd += " ist beigetreten.\r\n";
		mServer.broadcast(brd);
	}
	
	// Wenn der Rückgabewert false ist, wird die Verbindung beendet.
	protected Boolean handleMessage( String aMsg )
	{					
		if( aMsg.equals("QUIT") ) 
		{
			write("Verbindung getrennt\r\n");
			try {mSocket.close();} catch (IOException e) {}	
			return false;
		}	
		
		// Senden
		if( aMsg.startsWith("MSG ") )
		{						
			// MSG zunächst entfernen
			aMsg = aMsg.substring(4);
			
			// Broadcast zusammenbauen und senden
			mServer.broadcast( "MSG " + mSocket.getInetAddress().toString() + " sagt: " + aMsg + "\r\n");
			System.out.println(mSocket.getInetAddress().toString() + " sagt: " + aMsg );
		}
		else if( aMsg.startsWith("QUIT ") )
		{
			aMsg = aMsg.substring(5);
			write( "SERVERMSG " + aMsg );
			try {mSocket.close();} 
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			mServer.removeClient(this);
			return false;
		}			
		return true;
	}
	
	public void run()
	{		
		while(true)
		{
			// Nächste Daten lesen
			String s = null;
			try 
			{
				s = in.readLine();
			} catch (IOException e) 
			{				
				// Verbindung verloren
				System.out.println("Verbindung verloren: " + mSocket.getInetAddress().toString());
				mServer.removeClient(this);
				mServer.broadcast("CHANNELMSG " + mSocket.getInetAddress().toString() + " hat den Chat verlassen.\r\n");
				return;
			}			
			if( s != null )	if( !handleMessage(s) ) break;
			try {sleep(1);} catch (InterruptedException e) {}
		}
		mServer.removeClient(this);
	}
	
	public Boolean write(String aData) 
	{
		try 
		{
			out.writeBytes(aData);
		} catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
