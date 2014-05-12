
public class MessageFactory {

	public static TextMessage newTextMessage(String content) {
		TextMessage msg = original(content);
		msg.setColor(Color.instance.getColor());
		
		return msg;
	}
	
}
