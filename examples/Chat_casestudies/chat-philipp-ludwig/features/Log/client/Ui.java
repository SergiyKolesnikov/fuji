package client;

import common.Logging;

public class Ui 
{
	protected Logging mLog;
	
	public Ui()
	{
		mLog = new Logging();
		mLog.open("clientlog.txt");
	}
	
	public void addText(String aText)
	{
		original(aText);
		mLog.log(aText);
	}
}