package server;

import java.io.IOException;
import java.net.Socket;

import server.Server;

public class Client 
{
	protected String mUserName = "";
	public String getUserName() { return mUserName; }
	
	Client(Socket aSocket, Server aServer) throws IOException 
	{
		mUserName = mSocket.getInetAddress().toString();
	}
	
	protected Boolean handleMessage( String aMsg )
	{		
		// Falls der Benutzer nicht eingeloggt ist: Nur auf das Passwort warten.
		if(aMsg.startsWith("USERNAME "))
		{
			String uname = aMsg;
			uname = uname.replace("USERNAME ", "").trim();
			uname = uname.replace(" ", "_"); // Leerzeichen sind nicht gestattet.
			
			// Überprüfen, ob der Benutzername bereits vergeben ist. Wenn nicht setzen:
			if( !mServer.isUserNameAvailable(uname) ) write( "SERVERMSG Dieser Benutzername ist leider schon vergeben.\r\n" );
			else 
			{
				System.out.println(mUserName + " heißt nun: " + uname);
				mServer.broadcast( "CHANNELMSG " + mUserName + " heißt nun: " + uname + "\r\n" );
				mUserName = uname;
				write( "SERVERMSG Dein Name lautet nun: " + uname + "\r\n" );			
			}
			return true;
		}
		else if( aMsg.startsWith("MSG ") )
		{						
			// MSG zunächst entfernen
			aMsg = aMsg.substring(4);
			
			// Broadcast zusammenbauen und senden
			mServer.broadcast( "MSG " + mUserName + " sagt: " + aMsg + "\r\n");
			System.out.println(mUserName + " sagt: " + aMsg );
			return true;
		}
		else return original(aMsg);	
	}
}