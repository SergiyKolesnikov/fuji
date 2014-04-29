package client; 

public  class  Core  extends Thread {
	
	protected Ui mUi;

	
	protected Client mClient;

	
	protected Boolean mConnected;

	
	
	public Core()
	{
		mClient = new Client();
		mConnected = false;
		mUi = new Ui();
	}

		
		
	public void run()
	{
		while(true)
		{
			if( !mClient.isConnected() )
			{
				// Bestand schonmal eine Verbindung? Wenn ja, die GUI entsprechend aktualisieren.
				if( mConnected )
				{
					mUi.onDisconnected();
					mConnected = false;
				}
				
				// Warten, bis der Benutzer eine Serveradresse eingetragen hat.				
				String serverIp = "";
				while( serverIp.isEmpty() )
				{					
					serverIp = mUi.getServerIp();
					try { sleep(100); } catch (InterruptedException e) { return; }
				}
									
				try 
				{			
					// Serveradresse vom Benutzer abfragen und versuchen, die Verbindung herzustellen
					mClient.connect(serverIp);
				} 
				catch (Exception e) 
				{
					mUi.addText("Herstellen der Verbindung fehlgeschlagen: " + e.getMessage() + "\n");
					mUi.onDisconnected();
					continue;
				}	
				
				// Thread starten
				mClient.start();
				
				// Lokale Variable setzen
				mConnected = true;
				
				// GUI informieren.
				mUi.onConnected();
			}
			else
			{
				// Eintreffende Daten verarbeiten				
				if( mClient.hasData() )
				{
					// N�chste Zeile lesen
					String data = mClient.nextLine();
					
					// Beenden?
					if( data.equals("QUIT") ) return;
								
					// Kommando extrahieren
					String s = data.trim();
					Integer n = s.indexOf(" ");
					
					// Ung?ltige Nachricht?
					if( n == -1 ) continue;
					
					String cmd = s.substring( 0, n );
					cmd = cmd.trim();
					
					// Kommando aus String entfernen
					s = s.substring(cmd.length()+1);
					
					String guiText = "";
					if( cmd.equals("CONNECTED") ) 		guiText = "Verbindung hergestellt.\n";			
					else if( cmd.equals("SERVERMSG") ) 	guiText = "Nachricht vom Server: " + s + "\n";
					else if( cmd.equals("CHANNELMSG") )	guiText = "** " + s + "\n";
					else if( cmd.equals("MSG") ) 		guiText = s.trim() + "\n";
					else 								guiText = "Unbekannt: " + s.trim() + "\n";
					
					// An das UI weitergeben
					if(!data.isEmpty()) mUi.addText(guiText);
				}
				
				// Zu sendende Daten verarbeiten
				if( mUi.hasInput() )
				{
					// Lesen
					String msg = mUi.nextInput();
					if( msg.isEmpty() ) continue;					
					msg = "MSG " + msg;
									
					// Senden
					mClient.write(msg);
				}
			}
			try {sleep(1);} catch (InterruptedException e) {}
		}
	}


}
