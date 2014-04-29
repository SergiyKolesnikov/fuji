/**
 * TODO description
 */
public class Client {
	
	//public Cipher cipher = new CipherNone();
	
	private void processTextMessage(TextMessage msg, MessageContext context){
		msg.setContent(cipher.decode(msg.getContent()));

		original(msg, context);
	}
	
	public void send(TextMessage msg) {
		msg.setContent(cipher.encode(msg.getContent()));
		original(msg);
	}
	
}