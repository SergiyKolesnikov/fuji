package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ui extends Thread 
{
	protected Boolean mStopThread = false; // Wenn true wird der Thread gestoppt.

	public void addText(String aText) 
	{
		System.out.print(aText);
	}

	public void onDisconnected() 
	{
		// Thread, der auf Chateingaben wartet, stoppen.
		mStopThread = true;

		// Benutzer informieren.
		System.out.println("Verbindung zum Server wurde unterbrochen!");
	}

	public void run()
	{
		mStopThread = false;
		while(!mStopThread)
		{
			System.out.print("> ");

			String s = "";
			while( s.isEmpty() )
			{
				InputStreamReader converter = new InputStreamReader(System.in);
				BufferedReader in = new BufferedReader(converter);

				try 
				{
					s = in.readLine();
				} catch (IOException e) 
				{
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}

			if( s.equals("/quit") )
			{
				System.out.println("Programm wird beendet.");
				mStopThread = true;
				break;
			}
			else if( s.startsWith("/help") )
			{
				System.out.println("Verfügbare Kommandos: \n");
				System.out.println("\t/quit\tBeendet das Programm.");
				System.out.println("\t/enc\tWechselt die Verschl?sselung.\n");
			}
			else mBuffer.add(s);
		}		
	}

	public String getServerIp() 
	{
		System.out.print("Serveradresse [127.0.0.1]: ");
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		String server = null;
		try 
		{
			server = in.readLine();
		} catch (IOException e) 
		{
			server = "";
		}
		if( server.isEmpty() ) server = "127.0.0.1";	
		return server;
	}

	public void onConnected()
	{
		original();
		start();
	}
}