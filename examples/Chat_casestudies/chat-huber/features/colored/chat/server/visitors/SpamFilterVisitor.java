package chat.server.visitors;

import chat.AMessageVisitor;
import chat.IMessageVisitor;
import chat.messages.ColoredTextMessage;
import chat.messages.DefaultTextMessage;
import chat.messages.LoginMessage;
import chat.messages.StatusMessage;
import chat.messages.StatusMessage.Status;

public class SpamFilterVisitor extends AMessageVisitor {
	@Override
	public void visit(ColoredTextMessage message) {
		if (!isSpam(message.getContent())) {
			forward(message);
		} else {
			StatusMessage status = new StatusMessage(Status.SPAM);
			status.setClient(message.getClient());
			forward(status);
		}
	}

	private boolean isSpam(String text) {
		for (String key : SPAM_KEYWORDS) {
			if (text.toLowerCase().contains(key)) {
				return true;
			}
		}
		return false;
	}
}
