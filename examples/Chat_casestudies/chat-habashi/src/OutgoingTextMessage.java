
import java.awt.Color;



public class OutgoingTextMessage extends TextMessage{
	private static final long serialVersionUID = 1L;
	
	public OutgoingTextMessage(String text) {
		super(text);
	}
		
	public OutgoingTextMessage(String text, Color color){
		super(text, color);
	}
	
	public void encodeText(){
		((OutgoingTextMessage) this).text = invert(mix(((OutgoingTextMessage) this).text));
	}
	
	private String mix(String text){
		String res = text;
		text.replace('a', '#');
		text.replace('e', '=');
		text.replace('i', '-');
		text.replace('o', '<');
		text.replace('u', '>');
		return res;
	}
}