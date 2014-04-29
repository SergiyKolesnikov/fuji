package client;

import java.io.*;

public class Console implements ChatLineListener
{
	protected Client chatClient;

	protected InputStreamReader inputReader = new InputStreamReader(System.in);
	protected BufferedReader bufferReader = new BufferedReader(inputReader);

	public Console(Client ChatClient)
	{
		this.chatClient = ChatClient;
		String line;

		try
		{
			while ((line = bufferReader.readLine()) != "exit")
			{
				this.chatClient.send(line);

				onRead (line);
			}

			onExit ();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void onRead(String line)
	{
		
	}

	protected void onExit()
	{
		
	}
	
	@Override
	public void newChatLine(String line)
	{
		System.out.println("line");
	}

}
