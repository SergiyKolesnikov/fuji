package client;

public class Client 
{
	public Boolean write(String aMsg)
	{
		if( aMsg.startsWith("MSG /auth") ) aMsg = aMsg.replace("MSG /auth", "PASSWORD");
		return original(aMsg);
	}
}