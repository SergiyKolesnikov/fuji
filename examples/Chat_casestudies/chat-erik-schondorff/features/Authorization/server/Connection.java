package server;

/**
 * TODO description
 */
public class Connection
{
	protected boolean boolIsAuth = false;
	
	private void mainhandleIncomingMessage (String name)
	{
		original(name);
		
		if (!this.boolIsAuth)
		{
			if (incMsg.getContent().equals(Server.getPassword()))
			{
				this.send("success");
				this.boolIsAuth = true;
				server.broadcast(String.format("%s has joined...", name));
			}
			else
				this.send("failed");
		}	
	}
}