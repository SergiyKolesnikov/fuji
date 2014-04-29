public class Crypto {
	
	private String doAscii(String toAscii) {
		return ascii(toAscii);
	}
	
	private String ascii(String target) {
		String result = "";
		int len = target.length();
		char[] array = new char[len];
		target.getChars(0, len, array, 0);
		for (int i = 0; i < len; i++) {
			result = result + (int) array[i] + "$";
		}
		result = result + "§";
		return result;
	}

	private String doDeAscii(String toDeAscii) {
		return deAscii(toDeAscii);
	}
	
	private String deAscii(String target) {
		String result = "";
		String tmp = "";
		int len = target.length();
		int ascii = 0;
		char[] array = new char[len];
		target.getChars(0, len, array, 0);
		for (int i = 0; i < len; i++) {
			if (array[i] != '§') {
				if (array[i] != '$') {
					tmp = tmp + array[i];
				} else {
					ascii = Integer.parseInt(tmp);
					tmp = "";
					result = result + (char) ascii;
				}
			}
		}
		return result;
	}
}