package server; 

import java.io.BufferedReader; 
import java.io.DataOutputStream; 

import java.io.IOException; 
import java.io.InputStreamReader; 
import java.net.Socket; 

import server.Server; 

import common.EncryptROT13; 
import common.EncryptXOR; 

public   class  Client  extends Thread {
	
	private Socket mSocket;

	
	private BufferedReader in;

	
	private DataOutputStream out;

	
	private Server mServer;

	
	
	Client  (Socket aSocket, Server aServer) throws IOException 
	{
		mSocket = aSocket;
		mServer = aServer;	
		
		in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		out = new DataOutputStream(mSocket.getOutputStream());
		
		write("SERVERMSG Hallo " + mSocket.getInetAddress().toString() + "!\r\n");
		
		// Nachricht an den Chat �ber den neuen Teilnehmer
		String brd = "CHANNELMSG ";
		brd += mSocket.getInetAddress().toString();
		brd += " ist beigetreten.\r\n";
		mServer.broadcast(brd);
	
		mUserName = mSocket.getInetAddress().toString();
	
		write("SERVERMSG Login erforderlich!\r\n");
	}

	
	
	// Wenn der R�ckgabewert false ist, wird die Verbindung beendet.
	 private Boolean  handleMessage__wrappee__chat  ( String aMsg )
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
			// MSG zun�chst entfernen
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

	
	
	 private Boolean  handleMessage__wrappee__Spamfilter  ( String aMsg )
	{		
		for( int i=0; i<mFilter.length; i++ ) 
			if( aMsg.toLowerCase().contains(mFilter[i].toLowerCase()) ) 
			{
				write( "SERVERMSG Deine Nachricht wurde vom Spamfilter gefressen.\r\n" );
				return true;
			}
		
		return handleMessage__wrappee__chat(aMsg);
	}

	
	
	 private Boolean  handleMessage__wrappee__username  ( String aMsg )
	{		
		// Falls der Benutzer nicht eingeloggt ist: Nur auf das Passwort warten.
		if(aMsg.startsWith("USERNAME "))
		{
			String uname = aMsg;
			uname = uname.replace("USERNAME ", "").trim();
			uname = uname.replace(" ", "_"); // Leerzeichen sind nicht gestattet.
			
			// �berpr�fen, ob der Benutzername bereits vergeben ist. Wenn nicht setzen:
			if( !mServer.isUserNameAvailable(uname) ) write( "SERVERMSG Dieser Benutzername ist leider schon vergeben.\r\n" );
			else 
			{
				System.out.println(mUserName + " hei�t nun: " + uname);
				mServer.broadcast( "CHANNELMSG " + mUserName + " hei�t nun: " + uname + "\r\n" );
				mUserName = uname;
				write( "SERVERMSG Dein Name lautet nun: " + uname + "\r\n" );			
			}
			return true;
		}
		else if( aMsg.startsWith("MSG ") )
		{						
			// MSG zun�chst entfernen
			aMsg = aMsg.substring(4);
			
			// Broadcast zusammenbauen und senden
			mServer.broadcast( "MSG " + mUserName + " sagt: " + aMsg + "\r\n");
			System.out.println(mUserName + " sagt: " + aMsg );
			return true;
		}
		else return handleMessage__wrappee__Spamfilter(aMsg);	
	}

	
	 private Boolean  handleMessage__wrappee__PrivateMessaging  ( String aMsg )
	{		
		// �berpr�fen, ob eine private Nachricht vorliegt
		// Format: PRIVMSG Empf�nger Nachricht
		if( aMsg.startsWith("PRIVMSG") )			
		{
			aMsg = aMsg.replace("PRIVMSG ", "");
			
			// Empf�ngername
			String recv = aMsg.substring(0, aMsg.indexOf(" ") );
			aMsg = aMsg.substring(recv.length());
			
			// Absender
			String sender = mUserName;
			if( sender.isEmpty() ) sender = mSocket.getInetAddress().toString();
			
			// �berpr�fen, ob der Empf�nger bekannt ist.
			if( mServer.isUserNameAvailable(recv) )
			{
				write( "SERVERMSG Unbekannter Empf�nger: " + recv + "\r\n" );				
			}
			else if( !mServer.sendPrivateMessage( recv, sender, aMsg ) )
			{
				write( "SERVERMSG Senden der Nachricht fehlgeschlagen!\r\n" );
			}				
			return true;
		}
		// Falls keine private Nachricht vorliegt: Mit der �blichen Behandlung verfahren.
		else return handleMessage__wrappee__username(aMsg);		
	}

	
	
	 private Boolean  handleMessage__wrappee__Authentication  ( String aMsg )
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
		else return handleMessage__wrappee__PrivateMessaging(aMsg);	
	}

	
	 private Boolean  handleMessage__wrappee__GUI  ( String aMsg )
	{		
		String s = aMsg;
		if( s.startsWith("MSG ") ) s = s.substring(4);
		else return handleMessage__wrappee__Authentication(s);
		
		if( s.startsWith("XOR ") )
		{
			s = s.substring(4);
			s = EncryptXOR.decrypt(s);
		}
		else if( s.startsWith("ROT13 ") )
		{
			s = s.substring(6);
			s = EncryptROT13.decrypt(s);
		}
		
		return handleMessage__wrappee__Authentication("MSG " + s);
	}

	
	protected Boolean handleMessage( String aMsg )
	{		
		String s = aMsg;
		Integer blau_open = 0;
		while( s.indexOf("<blau>") != -1 )
		{
			s = s.replace("<blau>", "<font color='blue'>" );
			blau_open++;						
		}

		// Es m�ssen genausoviele font-Tags geschlossen wie ge�ffnet werden, um Missbrauch zu vermeiden.
		Integer blau_close = 0;
		while( s.indexOf("</blau>") != -1 )
		{
			s = s.replace("</blau>", "</font>" );
			blau_close++;						
		}
		for( int i=0; i<blau_open-blau_close; i++ ) s = s + "</font>";	
		return handleMessage__wrappee__GUI(s);
	}

	
	
	public void run()
	{		
		while(true)
		{
			// N�chste Daten lesen
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

	
	private final String[] mFilter = {"wort1", "wort2", "wort3", "wort4"};

	
	protected String mUserName = "";

	
	public String getUserName() { return mUserName; }

	
	protected Boolean mLoggedIn = false;

	
	protected final String mPassword = "123456";


}
