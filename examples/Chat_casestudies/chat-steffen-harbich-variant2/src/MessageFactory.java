
public   class  MessageFactory {
	

	public static TextMessage  newTextMessage__wrappee__History  (String content) {
		TextMessage msg = new TextMessageImpl();
		msg.setContent(content);
		
		return msg;
	}

	

	public static TextMessage newTextMessage(String content) {
		TextMessage msg = newTextMessage__wrappee__History(content);
		msg.setColor(Color.instance.getColor());
		
		return msg;
	}


}
