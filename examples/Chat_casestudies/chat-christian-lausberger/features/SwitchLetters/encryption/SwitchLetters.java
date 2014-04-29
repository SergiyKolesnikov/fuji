package encryption;

public class SwitchLetters extends Encryption{

	public String encode(String text) {
		String encode = text;
		if (text.length() >= 2){
			char c,d;
			c = text.charAt(1);
			d = text.charAt(0);
			encode = String.valueOf(c) + String.valueOf(d) + text.substring(2);
		}
		return encode;
	}
	
	public String decode(String text) {
		return encode(text);
	}
}
