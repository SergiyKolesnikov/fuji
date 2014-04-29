package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client extends Thread
{
	private Socket mSocket;
	private BufferedReader in;
	private DataOutputStream out;
	private ArrayList<String> mBuffer;
	
	// Wenn diese Variable true wird, wird der Thread beendet.
	private Boolean mStop;
	
	public enum EncryptionType{ None, XOR, ROT13 };
	
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
		// Dadurch ist es möglich, den Thread halbwegs sauber zu beenden.
		mSocket.setSoTimeout(5000);
		
		in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		out = new DataOutputStream(mSocket.getOutputStream());				
	}
	
	public void disconnect()
	{
		mStop = true;
	}
	
	public Boolean write(String aMsg)
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