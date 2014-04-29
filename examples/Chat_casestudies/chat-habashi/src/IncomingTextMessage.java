
import java.awt.Color;



public class IncomingTextMessage extends TextMessage{
	private static final long serialVersionUID = 1L;
	
	public IncomingTextMessage(String text) {
		super(text);
	}
		
	public IncomingTextMessage(String text, Color color){
		super(text, color);
	}
	
	public String decodeText(){
		return mix(invert(text));
	}
	
	private String mix(String text){
		String res = text;
		text.replace('#', 'a');
		text.replace('=', 'e');
		text.replace('-', 'i');
		text.replace('<', 'o');
		text.replace('>', 'u');
		return res;
	}
}