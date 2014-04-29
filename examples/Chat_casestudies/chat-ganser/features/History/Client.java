import java.io.IOException;

public class Client {

    protected void handleIncomingMessage(Object msg) {

	if (msg instanceof TextMessage) {
	    TextMessage message = (TextMessage) msg;

	    try {
		History.log(message);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	original(msg);
    }
}
