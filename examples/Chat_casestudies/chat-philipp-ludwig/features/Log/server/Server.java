package server;

import java.io.IOException;

import common.Logging;
import server.Client;

public class Server 
{
	protected Logging mLog;
	
	public Server()
	{
		mLog = new Logging();
		mLog.open("serverlog.txt");
	}
	
	public void print(String str)
	{
		original(str);
		
		// Wenn der Server noch startet, findet bereits ein print() Aufruf statt, bevor das Objekt initialisiert ist.
		if( mLog != null ) mLog.log(str + "\n");
	}
	
	public void broadcast(String aMsg) 
	{
		original(aMsg);
		mLog.log(aMsg);
	}
}