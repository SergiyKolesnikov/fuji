package client;

import common.TextMessage;

/**
 * TODO description
 */
public class Client
{
	protected boolean boolIsAuth = false;

	protected void modifyIncomingMessage()
	{
		original ();
		
		if (!this.boolIsAuth)
		{
			if (!incMsg.getContent().equals("success"))
			{
//				incMsg.setContent("Authorization has failed. Type the password again...");
				fireAddLine("Authorization has failed. Type the password again...");
				return;
			}
			else
			{
				fireAddLine("Authorization was successful...");
				this.boolIsAuth = true;
			}
		}
	}

}