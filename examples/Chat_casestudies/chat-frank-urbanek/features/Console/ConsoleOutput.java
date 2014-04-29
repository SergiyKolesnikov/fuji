
public class ConsoleOutput implements ChatLineListener{
	
	
	
	@Override
	public void newChatLine(String line) {
		System.out.println(line);
	}
	
	@Override
	public void newTextMessage(TextMessage msg, MessageContext context){
		System.out.println(
				String.format("%s: %s", context.getInfo().getName(), msg.getContent())
				);
	}

}
