package common;

/**
 * TODO description
 */
public class TextMessage {
	private String reciever;
	public boolean privatMessage = false;
	
	
	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.privatMessage = true;
		this.reciever = reciever;
	}
}