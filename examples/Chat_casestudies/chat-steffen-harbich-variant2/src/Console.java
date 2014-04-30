import java.util.Scanner; 
public   class  Console  implements ChatLineListener {
	

	public Console(ClientConnection clientConnection)
	{
		clientConnection.addLineListener(this);
		runInputLoop(clientConnection);
	}

	
	
	private void runInputLoop(ClientConnection clientConnection) {
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		
		while(clientConnection.isAlive() && input.equals("exit") == false) {
			clientConnection.send(MessageFactory.newTextMessage(input));
			input = sc.nextLine();
		}
		
		if (clientConnection.isAlive()) {
			clientConnection.close();
		}
	}

	

	public void  newChatLine__wrappee__Console  (TextMessage line) {
		System.out.println(line.getContent());
	}

	

	public void newChatLine(TextMessage line) {
		newChatLine__wrappee__Console(MessageFactory.newTextMessage(line.getContent() + " [" + line.getColor() + "]"));
	}


}
