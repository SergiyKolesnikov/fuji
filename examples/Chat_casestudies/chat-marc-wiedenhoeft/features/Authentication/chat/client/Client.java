package chat.client;

import chat.client.AuthenticationDialog;

public class Client 
{
	private boolean isAuthenticated = false; //is client authenticated at server?

	public void init(String host, int port)
	{
		original(host, port);
		
		/**
		 * User can enter name and password.
		 */
		AuthenticationDialog theDialog = new AuthenticationDialog(this);
		theDialog.setLocation(200, 200);
		theDialog.setModal(true);
		theDialog.setVisible(true);
	}
}