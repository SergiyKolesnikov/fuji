package server;

/**
 * TODO description
 */
public class Connection
{
	private void prehandleIncomingMessage (String name)
	{
		incMsg.Decode();
		
		original (name);
	}
	
	public void send(TextMessage msg)
	{
		msg.Encode();
		
		original(msg);
	}
}