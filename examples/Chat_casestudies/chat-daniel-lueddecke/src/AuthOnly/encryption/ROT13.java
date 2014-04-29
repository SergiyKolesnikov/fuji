package encryption; 

public  class  ROT13 {
	
	
	public static String encrypt(String text) {
		return rotateBy(text, 13);		
	}

	
	
	public static String decrypt(String text) {
		return rotateBy(text, 13);		
	}

	
	
	private static String rotateBy(String text, int count) {
		StringBuilder ret = new StringBuilder();		
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			int newC = 0;
			if(c >= 'A' && c <= 'Z')
				newC = ((c - 'A' + count) % 26 + 'A');
			else if (c >= 'a' && c <= 'z')
				newC = ((c - 'a' + count) % 26 + 'a');
			else
				newC = c;
			
			ret.append((char)newC);
		}
		
		return ret.toString();
	}


}
