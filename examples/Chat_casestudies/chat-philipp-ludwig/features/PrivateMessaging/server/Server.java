package server;

public class Server 
{
	public Boolean sendPrivateMessage(String aRecv, String aSender, String aMsg)
	{
		for( int i=0; i<mClients.size(); i++ ) if( mClients.get(i).getUserName().equals(aRecv) ) 
			return mClients.get(i).write("MSG Private Nachricht von " + aSender + ": " + aMsg + "\r\n" );
		
		return false;
	}
}