package client; 

import java.io.BufferedReader; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.net.Socket; 
import java.net.SocketTimeoutException; 
import java.net.UnknownHostException; 
import java.util.ArrayList; 

import common.EncryptROT13; 
import common.EncryptXOR; 

public   class  Client  extends Thread {
	
	private Socket mSocket;

	
	private BufferedReader in;

	
	private DataOutputStream out;

	
	private ArrayList<String> mBuffer;

	
	
	// Wenn diese Variable true wird, wird der Thread beendet.
	private Boolean mStop;

	
	
	public enum  EncryptionType { None ,  XOR ,  ROT13}

	;

	
	
	Client()
	{		
		mBuffer = new ArrayList<String>();
		mStop = false;
	}

	
	
	public Boolean isConnected() 
	{
		if( mSocket == null ) return false;
		
		return mSocket.isConnected();
	}

	
	public Boolean hasData() { return !mBuffer.isEmpty(); }

	
	public String nextLine() { return mBuffer.remove(0); }

	
	
	public void connect(String aIp) throws UnknownHostException, IOException
	{
		mSocket = new Socket(aIp, 4444);
		
		// Es wird ein Timeout von f?nf Sekunden auf das Socket gesetzt, damit die Hauptschleife
		// des Threads wiederholt durchlaufen werden kann und von readLine() nicht geblockt wird.
		// Dadurch ist es m�glich, den Thread halbwegs sauber zu beenden.
		mSocket.setSoTimeout(5000);
		
		in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		out = new DataOutputStream(mSocket.getOutputStream());				
	}

	
	
	public void disconnect()
	{
		mStop = true;
	}

	
	
	 private Boolean  write__wrappee__Spamfilter  (String aMsg)
	{
		try 
		{
			out.writeBytes(aMsg + "\r\n");
			return true;
		} catch (IOException e) 
		{
			System.out.println("Nachricht wurde nicht gesendet: " + aMsg);
			return false;
		}
	}

		
	 private Boolean  write__wrappee__username  (String aMsg)	
	{
		// �nderung des Benutzernamens beim Server anfragen
		if( aMsg.startsWith("MSG /username") ) aMsg = aMsg.replace("MSG /username", "USERNAME");
		return write__wrappee__Spamfilter(aMsg);
	}

	
	 private Boolean  write__wrappee__PrivateMessaging  (String aMsg)
	{
		if( aMsg.startsWith("MSG /msg") ) aMsg = aMsg.replace("MSG /msg", "PRIVMSG");
		return write__wrappee__username(aMsg);
	}

	
	 private Boolean  write__wrappee__Authentication  (String aMsg)
	{
		if( aMsg.startsWith("MSG /auth") ) aMsg = aMsg.replace("MSG /auth", "PASSWORD");
		return write__wrappee__PrivateMessaging(aMsg);
	}

	
	public Boolean write(String aMsg)
	{
		if( aMsg.startsWith("MSG /rot13 ") ) 
		{
			String data = aMsg.substring(11);	
			aMsg = "MSG ROT13 " + EncryptROT13.encrypt(data);
		}
		else if( aMsg.startsWith("MSG /xor ") )
		{
			String data = aMsg.substring(8);
			aMsg = "MSG XOR " + EncryptXOR.encrypt(data);
		}
		return write__wrappee__Authentication(aMsg);
	}

	
	
	public void run()
	{
		while(!mStop)
		{
			// N?chste Daten lesen
			String s = null;
			try 
			{
				s = in.readLine();
			} catch(SocketTimeoutException e)
			{
				// Nichts tun, da Timeouts geplant sind. Im n?chsten Schleifendurchlauf wird es erneut versucht.
				continue;
			} catch (IOException e)
			{		
				// Dies bedeutet, dass die Verbindung zum Server unterbrochen ist.
				mStop = true;
				break;
			} 
			
			if( s == null ) 
			{
				mStop = true;
				break;
			}
			
			mBuffer.add(s);
		}
		
		try {
			if( mSocket.isConnected() ) mSocket.close();
		} catch (IOException e) {
			;
		}
		mSocket = null;
	}


}
