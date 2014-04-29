import java.awt.Color; 

public  class  ChatMessage  extends Message {
	
	
	private String msg;

	

	public ChatMessage(String source, String msg) {
		super(source);
		this.setMsg(msg);
	}

	

	public void setMsg(String msg) {
		this.msg = msg;
	}

	

	public String getMsg() {
		return msg;
	}

	

	public String toString() {
		return "Chatmessage || Source: " + getSource() + " | Message: "
				+ getMsg();
	}


}
