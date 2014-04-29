package server;

public class Client 
{
	protected Boolean handleMessage( String aMsg )
	{		
		// Überprüfen, ob eine private Nachricht vorliegt
		// Format: PRIVMSG Empfänger Nachricht
		if( aMsg.startsWith("PRIVMSG") )			
		{
			aMsg = aMsg.replace("PRIVMSG ", "");
			
			// Empfängername
			String recv = aMsg.substring(0, aMsg.indexOf(" ") );
			aMsg = aMsg.substring(recv.length());
			
			// Absender
			String sender = mUserName;
			if( sender.isEmpty() ) sender = mSocket.getInetAddress().toString();
			
			// Überprüfen, ob der Empfänger bekannt ist.
			if( mServer.isUserNameAvailable(recv) )
			{
				write( "SERVERMSG Unbekannter Empfänger: " + recv + "\r\n" );				
			}
			else if( !mServer.sendPrivateMessage( recv, sender, aMsg ) )
			{
				write( "SERVERMSG Senden der Nachricht fehlgeschlagen!\r\n" );
			}				
			return true;
		}
		// Falls keine private Nachricht vorliegt: Mit der üblichen Behandlung verfahren.
		else return original(aMsg);		
	}	
}