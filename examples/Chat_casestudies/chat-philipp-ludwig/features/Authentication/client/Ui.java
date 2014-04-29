package client;

public class Ui 
{
	public void onConnected()
	{
		original();
		addText( "Login erforderlich. Passwort eingabe via: /auth <Passwort>\n" );
	}
}