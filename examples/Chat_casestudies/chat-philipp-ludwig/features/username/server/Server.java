package server;

public class Server 
{
	public Boolean isUserNameAvailable(String aUserName)
	{
		for( int i=0; i<mClients.size(); i++ ) if( mClients.get(i).getUserName().equals(aUserName) ) return false;
		
		return true;
	}
}