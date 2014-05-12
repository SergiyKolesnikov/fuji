
public class Console {

	public void newChatLine(TextMessage line) {
		original(MessageFactory.newTextMessage(line.getContent() + " [" + line.getColor() + "]"));
	}
	
}
