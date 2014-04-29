package common; 

public   class  PrivateMessage  extends TextMessage {
	
	public String textForLog() {
		return "Private message from " + getFrom() + ": " + getText() + "\n";
	}

	
	private String to;

	
	
	public PrivateMessage(String ito, String from, String text) {
		super(from, text);
		to = ito;
	}

	
	
	public String getTo() {
		return to;
	}


}
