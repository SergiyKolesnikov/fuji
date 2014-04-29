

public class Console implements ChatLineListener {

	private Client chatClient;
	
	public Console(Client chatClient) {
		this.chatClient = chatClient;
		System.out.println("starting console chat client...");
		chatClient.addLineListener(this);
	}
	
	@Override
	public void newChatLine(String line) {
		System.out.println(line);
		
	}
	
}