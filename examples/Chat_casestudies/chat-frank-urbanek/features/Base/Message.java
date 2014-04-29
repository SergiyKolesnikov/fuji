import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * serializable message that can be send over the sockets between client and
 * server.
 */
public abstract class Message implements Serializable {

	Map<String, Object> properties = new HashMap<String, Object>();
	
	protected MessageContext context;

	public Object getProperty(String key) {
		return properties.get(key);
	}

	public boolean addProperty(String key, Object value) {
		if (properties.containsKey(key))
			return false;
		properties.put(key, value);
		return true;
	}

	public boolean setProperty(String key, Object value) {
		Object ret = properties.put(key, value);
		return true;
	}

	public Message() {
	}

	public Message(MessageContext context) {
		super();
		this.context = context;
	}

	private static final long serialVersionUID = 1L;

	public MessageContext getContext() {
		return context;
	}

	public void setContext(MessageContext context) {
		this.context = context;
	}

	/*
	 * private ContentMessage payload;
	 * 
	 * public Message(ContentMessage payload) { super(); this.payload = payload;
	 * }
	 */

}
