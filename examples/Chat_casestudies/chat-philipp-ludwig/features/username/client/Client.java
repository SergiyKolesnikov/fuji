package client;

public class Client 
{	
	public Boolean write(String aMsg)	
	{
		// Änderung des Benutzernamens beim Server anfragen
		if( aMsg.startsWith("MSG /username") ) aMsg = aMsg.replace("MSG /username", "USERNAME");
		return original(aMsg);
	}
}