
public class Connection {

    private void handleIncomingMessage(String name, Object msg) {
	
	if (msg instanceof TextMessage) {
	    	TextMessage message = (TextMessage) msg;
	    	String color = message.getColor();
	    	String text = message.getContent();
	    	
	    	if (color != null) {
	    	    text = "<" + color + ">" + text + "</" + color + ">";
	    	    msg = new TextMessage(text);
	    	}
	}
	original(name, msg);
    }
}
