package client;

/**
 * TODO description
 */
public class Client
{
	protected String username = null;
	
	protected void modifyIncomingMessage()
	{
		original ();
		
		if (incMsg.getContent().startsWith("/msg "))
		{
			String[] split = incMsg.getContent().split(" ");
			if ((this.username != null) && (split[1].equals(this.username)))
			{
				String content = "";
				for (int i = 2; i < split.length - 1; i++)
					content += split[i] + " ";
				
				content += split[split.length - 1];
				
				incMsg.setContent(content);
			}
			else
				return;
		}
	}
	
	public void send(String line)
	{
		// User want to change / set name
		if (line.startsWith("/username"))
		{
			String[] split = line.split(" ");
			
			if ((split.length == 2) && (split[0].equals("/username")))
			{
				this.username = split[1];
				fireAddLine("Your username was changed / set to " + this.username);
			}
			else
			{
				fireAddLine("Only one argument is expected after /username. Further more your username must not contain white spaces.");
				return;
			}
		}
		
		original (line);
	}
}