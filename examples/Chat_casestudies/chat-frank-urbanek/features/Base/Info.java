
import java.io.Serializable;

public abstract class Info implements Serializable {
	protected static final long serialVersionUID = 1L;

	protected String name;

	public Info() {
		super();
		name = "";
	}
	
	public Info(String name) {
		this();
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
