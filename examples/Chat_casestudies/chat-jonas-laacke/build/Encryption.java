



public abstract class Encryption {
	
	/**
	 * encrypts a text
	 */
	public static String encrypt(String text){
		String enctext;
		enctext=encryptReverse(text);
		enctext= switchFirstLetters(enctext);
		return enctext;
	}
	
	/**
	 * encrypts a text by reversing
	 */
	private static String encryptReverse(String text){
		String rev="";
		for(int i=text.length()-1;i>=0;i--)
			rev+=text.charAt(i);
		return rev;
	}

	/**
	 * encrypts a text by switching the first two letters
	 */
	private static String switchFirstLetters(String text){
		if(text.length()==1)
			return text;
		else{
			String newtext="";
			newtext+=text.charAt(1);
			newtext+=text.charAt(0);
			for(int i=2;i<text.length();i++)
				newtext+=text.charAt(i);
			return newtext;
		}
	}
	
	/**
	 * decrypts a text
	 */
	public static String decrypt(String text){
		String dectext;
		dectext=switchFirstLetters(text);
		dectext=encryptReverse(dectext);
		return dectext;
	}

}