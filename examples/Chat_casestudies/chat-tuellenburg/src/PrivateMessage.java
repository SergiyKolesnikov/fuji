

public  class PrivateMessage extends Message {
	private String message;
	
	public PrivateMessage(String line) {
		this.message = line;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		((PrivateMessage) this).message = message;
	}
}