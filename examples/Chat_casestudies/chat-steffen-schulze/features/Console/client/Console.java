package client;

public class Console implements ChatLineListener {

	public Console(Client chatClient)
	{
		chatClient.addLineListener(this);
	}
	
	public void newChatLine(String line) 
	{
		System.out.println(line);
	}

}
