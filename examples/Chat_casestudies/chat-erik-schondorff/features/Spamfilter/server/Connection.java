package server;

/**
 * TODO description
 */
public class Connection
{
	private String[] spamList = new String[]
	{ " ad ", " advertisement ", " buy viagra " };

	private void mainhandleIncomingMessage(String name)
	{
		for (String spamWord : spamList)
		{
			if (incMsg.getContent().indexOf(spamWord) >= 0)
				return;
		}
		
		original (name);
	}
}