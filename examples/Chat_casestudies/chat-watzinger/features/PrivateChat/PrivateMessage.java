import java.io.Serializable;

import java.awt.Color;

public class PrivateMessage extends ChatMessage {
	private String target;
	
	public PrivateMessage(String source, String target,String msg) {
		super(source,msg);
		this.target = target;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}

	public String toString() {
		return "PrivateMessage || Source: " + getSource() + " | Message: "
				+ getMsg()+ " | Target: "+target;
	}
}

