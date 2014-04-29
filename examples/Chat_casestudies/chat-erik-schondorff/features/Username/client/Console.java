package client;

/**
 * TODO description
 */
public class Console
{
	public Console(Client ChatClient)
	{
		System.out.append("You can set or change your username at every time. Type /username <yourUsername>...");
		original(ChatClient);
	}
}