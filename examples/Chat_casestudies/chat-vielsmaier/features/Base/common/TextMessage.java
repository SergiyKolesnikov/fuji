package common;

public class TextMessage extends Message {
	private static final long serialVersionUID = -2543384070161019687L;
	private String from;
	private String text;
	
	public TextMessage(String ifrom, String itext) {
		from = ifrom;
		text = itext;
	}
	
	public String getText() {
		return text;
	}

	public String getFrom() {
		return from;
	}
	
	public void setFrom(String s) {
		from = s;
	}
}
