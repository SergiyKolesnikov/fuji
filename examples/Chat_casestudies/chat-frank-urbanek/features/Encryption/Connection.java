/**
 * TODO description
 */
public class Connection {

	private void handleMessage(Message msg) {
		if (msg instanceof ContentMessage) {
			ContentMessage message = (ContentMessage) msg;
			String content = (String) (message.getPayload().getContent());
			
				message.getPayload().setContent(
						server.getCipher().decode(content));
				message.setIsCrypted(false);
			
		}
		original(msg);
	}

	public void send(Message msg) {
		if (!msg.isCrypted()) {
			if (msg instanceof ContentMessage) {
				ContentMessage message = (ContentMessage) msg;
				String content = (String) (message.getPayload().getContent());
				message.getPayload().setContent(
						server.getCipher().encode(content));
				message.setIsCrypted(true);
			}
		}
		original(msg);
	}
}