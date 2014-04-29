package client;

import java.util.ArrayList;

public class Ui 
{
	private String mIp = "";
	protected void saveIp(String aIp) { mIp = aIp; }
	
	public void onDisconnected()
	{
		addText( "Verbindung getrennt.\n" );
	}
	
	public void onConnected()
	{
		addText( "Verbindung hergestellt.\n" );
	}
	
	public void addText(String aText)
	{
		
	}
	
	// Gibt die vom Benutzer eingegebene Server-IP zurück.
	public String getServerIp() { String tmp = mIp; mIp = ""; return tmp; }
		
	// Buffer für ausgehende Daten
	protected ArrayList<String> mBuffer = new ArrayList<String>();
	public Boolean hasInput() {	return !mBuffer.isEmpty(); }
	public String nextInput() { return mBuffer.remove(0); }
}