package encryption;

public class SWITCH {
	
	public static String encrypt(String text) {
		return switchCharacter(text, 0, 1);	
	}
	
	public static String decrypt(String text) {
		return switchCharacter(text, 0, 1);
	}
	
	private static String switchCharacter(String text, int c1, int c2) {
		if(c1 > text.length()-1 || c2 > text.length()-1)
			return text;
		
		char[] chars = new char[text.length()]; 	
		text.getChars(0, text.length(), chars, 0);
		char c = chars[c1];
		chars[c1] = chars[c2];
		chars[c2] = c;
		
		return new String(chars);
	}

}
