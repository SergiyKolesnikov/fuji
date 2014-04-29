package common;

/**
 * TODO description
 */
public class ROT13 {
	public String crypt(String str) {
		String temp = "";
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) < 78 || (str.charAt(i) > 96 && str.charAt(i) < 110))
				temp += (char)(str.charAt(i) + 13);
			else
				temp += (char)(str.charAt(i) - 13);
		}
		return temp;
	}
}