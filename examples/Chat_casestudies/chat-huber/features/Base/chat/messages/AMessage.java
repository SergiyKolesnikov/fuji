package chat.messages;

import java.io.Serializable;

import chat.IClient;
import chat.IMessageVisitor;

public abstract class AMessage implements Serializable {
	private static final long serialVersionUID = 9136607692008673214L;
	private String origin;
	private transient IClient client = null;

	public IClient getClient() {
		return client;
	}

	public void setClient(IClient client) {
		this.client = client;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public abstract void accept(IMessageVisitor visitor);
}
