
import java.io.Serializable;


public class MessageContext implements Serializable{
	private Info info;

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public MessageContext(Info info) {
		super();
		this.info = info;
	}
	
}
