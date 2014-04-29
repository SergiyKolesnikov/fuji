package server;

public class Client 
{
	private final String[] mFilter = {"wort1", "wort2", "wort3", "wort4"};
	
	protected Boolean handleMessage( String aMsg )
	{		
		for( int i=0; i<mFilter.length; i++ ) 
			if( aMsg.toLowerCase().contains(mFilter[i].toLowerCase()) ) 
			{
				write( "SERVERMSG Deine Nachricht wurde vom Spamfilter gefressen.\r\n" );
				return true;
			}
		
		return original(aMsg);
	}
}