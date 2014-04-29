package server;

import java.io.IOException;
import java.net.Socket;

import server.Server;

public class Client 
{
	protected Boolean mLoggedIn = false;
	protected final String mPassword = "123456";
	
	Client(Socket aSocket, Server aServer) throws IOException 
	{
		write("SERVERMSG Login erforderlich!\r\n");
	}
	
	protected Boolean handleMessage( String aMsg )
	{		
		// Falls der Benutzer nicht eingeloggt ist: Nur auf das Passwort warten.
		if( !mLoggedIn )
		{
			if( aMsg.startsWith("PASSWORD") )
			{
				aMsg = aMsg.replace("PASSWORD ", "");
				if( aMsg.equals(mPassword) ) 
				{
					mLoggedIn = true;
					write( "SERVERMSG Erfolgreich eingeloggt.\r\n" );
				}
				else write( "SERVERMSG Falsches Passwort.\r\n" );
			}
			// Falls kein Passwort gesendet wurde: Nachricht an den Benutzer
			else write( "SERVERMSG Bitte logge dich mit dem korrekten Passwort ein.\r\n" );
			
			return true;
		}
		else return original(aMsg);	
	}
}