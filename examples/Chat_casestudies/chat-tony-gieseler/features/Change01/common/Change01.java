package common;

/**
 * TODO description
 */
public class Change01 {
	public String crypt(String str) {
		if(str.length() > 1)
			str =  "" + str.charAt(1) + str.charAt(0) + str.substring(2, str.length());
		return str;
	}
}