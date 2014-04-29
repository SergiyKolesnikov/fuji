package common.message;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
