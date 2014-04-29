public class Crypto {
	
	private String doReverse(String toReverse) {
		return reverse(toReverse);
	}
	
	private String reverse(String target) {
		String result = "";
		int limit = 0;
		int len = target.length();
		char[] array = new char[len];
		target.getChars(0, len, array, 0);
		for (int i = len - 1; i >= limit; i--) {
			result = result + array[i];
		}
		return result;
	}
	
}