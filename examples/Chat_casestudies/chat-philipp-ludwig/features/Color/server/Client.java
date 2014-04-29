package server;

public class Client 
{
	protected Boolean handleMessage( String aMsg )
	{		
		String s = aMsg;
		Integer blau_open = 0;
		while( s.indexOf("<blau>") != -1 )
		{
			s = s.replace("<blau>", "<font color='blue'>" );
			blau_open++;						
		}

		// Es müssen genausoviele font-Tags geschlossen wie geöffnet werden, um Missbrauch zu vermeiden.
		Integer blau_close = 0;
		while( s.indexOf("</blau>") != -1 )
		{
			s = s.replace("</blau>", "</font>" );
			blau_close++;						
		}
		for( int i=0; i<blau_open-blau_close; i++ ) s = s + "</font>";	
		return original(s);
	}
}