

import java.io.Serializable;
import java.util.HashMap;



abstract public class AbstractMessage implements Message, Serializable {
	private static final long serialVersionUID = -8098393760434505847L;

	protected HashMap attributes = new HashMap();

	public void setAttribute(String name, Object value) {
		if (!(value instanceof Serializable)) {
			throw new RuntimeException("Can only use serializable values!");
		}

		attributes.put(name, value);
	}

	public Object getAttribute(String name) {
		if (hasAttribute(name)) return attributes.get(name);
		return null;
	}

	public boolean hasAttribute(String name) {
		return attributes.containsKey(name);
	}

	public void removeAttribute(String name) {
		attributes.remove(name);
	}
}