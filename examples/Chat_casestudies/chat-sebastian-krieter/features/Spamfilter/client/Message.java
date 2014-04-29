package client;

public class Message {
	private boolean valid = true;
	
	public boolean isValid() {
		return valid;
	}

	public void setInvalid() {
		valid = false;
	}
}