package client;

import java.io.BufferedWriter;

/**
 * TODO description
 */
public class Console
{
	private BufferedWriter logWrite;

	public Console(Client ChatClient)
	{
		try
		{
			this.logWrite = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("logFile.txt")));
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
			System.exit(0);
		}

		original();
	}
	
	protected void onRead (String line)
	{
		original ();
		
		writeLineToLog (line);
	}
	
	protected void onExit()
	{
		this.logWrite.close();
	}
	
	public void newChatLine(String line)
	{
		original ();
		
		writeLineToLog (line);
	}
	
	protected void writeLineToLog(String line)
	{
		try
		{
			this.logWrite.write(line);
			this.logWrite.newLine();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}