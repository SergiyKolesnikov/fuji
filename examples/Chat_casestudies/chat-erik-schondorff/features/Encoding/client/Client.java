package client;

/**
 * TODO description
 */
public class Client
{
	protected void modifyIncomingMessage ()
	{
		incMsg.Decode();
		original ();
	}
	
	public void send(TextMessage msg)
	{
		msg.Encode();
		
		original(msg);
	}
}