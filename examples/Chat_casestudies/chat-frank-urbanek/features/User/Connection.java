/**
 * TODO description
 */
public class Connection {
	String name = "default_user";

	public String getUserName() {
		return name;
	}

	private void handleMessage(Message msg) {
		if (msg instanceof ContentMessage) {
			ContentMessage message = (ContentMessage) msg;
			String text = (String) (message.getPayload().getContent());
			if (text.startsWith("/username")) {
				this.name = text.split(" ")[1]; // "/username bob"
			} else if (text.startsWith("/msg")) { // /msg alice Hi wie gehts?
				String name = text.split(" ")[1]; // "/username bob"
				String content = text.split(" ")[2]; // "/username bob"
				message.getPayload().setContent(content);
				server.sendMessage(message, name);
			} else {
				original(msg);
			}
		}
	}
}