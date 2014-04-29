import java.util.logging.Level;

/**
 * TODO description
 */
public class Client {
	Logging logging;

	/*
	private ContentMessage processContentMessage(ContentMessage msg) {
		log.log(Level.INFO, msg.getContext().getInfo().getName() + ": " + msg.getPayload().getContent());
		return original(msg);
	}
	*/

	private void processTextMessage(TextMessage msg, MessageContext context){
		logging.getLogger().log(Level.INFO, context.getInfo().getName() + ": " + msg.getContent());
		original(msg, context);
	}
	
	private void init() {
		logging = new Logging();
	}

}