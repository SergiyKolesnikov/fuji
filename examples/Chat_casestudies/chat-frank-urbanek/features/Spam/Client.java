/**
 * TODO description
 */
public class Client {
	
	//public Cipher cipher = new CipherNone();
	
	SpamFilter filter = new SpamFilter();
	
	private void processTextMessage(TextMessage msg, MessageContext context){
		if (!filter.isSpam(msg.getContent())){
			original(msg, context);
		}
	}
	
}