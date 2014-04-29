package chat.server.visitors;

import chat.AMessageVisitor;
import chat.IMessageVisitor;
import chat.messages.ColoredTextMessage;
import chat.messages.DefaultTextMessage;
import chat.messages.LoginMessage;
import chat.messages.StatusMessage;
import chat.messages.StatusMessage.Status;

public class SpamFilterVisitor extends AMessageVisitor {
	private static final String[] SPAM_KEYWORDS = new String[] { "spam", "foo",
			"bar" };

	public SpamFilterVisitor(IMessageVisitor next) {
		super(next);
	}

	@Override
	public void visit(LoginMessage message) {
		forward(message);
	}

	@Override
	public void visit(DefaultTextMessage message) {
		if (!isSpam(message.getContent())) {
			forward(message);
		} else {
			StatusMessage status = new StatusMessage(Status.SPAM);
			status.setClient(message.getClient());
			forward(status);
		}
	}

	@Override
	public void visit(StatusMessage message) {
		forward(message);
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
