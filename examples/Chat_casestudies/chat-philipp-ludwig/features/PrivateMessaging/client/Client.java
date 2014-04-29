package client;

public class Client
{
	public Boolean write(String aMsg)
	{
		if( aMsg.startsWith("MSG /msg") ) aMsg = aMsg.replace("MSG /msg", "PRIVMSG");
		return original(aMsg);
	}
}