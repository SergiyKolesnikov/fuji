package client;

public class Ui 
{
	public void onConnected()
	{
		original();
		addText( "Um verschlüsselte Nachrichten zu senden, schreibe: /xor <Text> oder /rot13 <Text>\n" );
	}
}