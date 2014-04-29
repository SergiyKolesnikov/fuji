package model;

public class Crypto {
	protected String ip = "";

	public String encrypt(String target) {
		String result = target;
		result = doReverse(result);
		result = doAscii(result);
		return result;
	}

	public String decrypt(String target) {
		String result = "";
		result = eraseIP(target);
		result = doDeAscii(result);
		result = doReverse(result);
		result = ip + " " + result;
		return result;
	}

	private String doReverse(String toReverse) {
		return toReverse;
	}
	
	private String doAscii(String toAscii) {
		return toAscii;
	}
	
	private String doDeAscii(String toDeAscii) {
		return toDeAscii;
	}

	private String eraseIP(String target) {
		ip = "";
		String result = "";
		int indexOfIpEnd = 0;
		int len = target.length();
		char[] array = new char[len];
		target.getChars(0, len, array, 0);
		while (array[indexOfIpEnd] != ':') {
			ip = ip + array[indexOfIpEnd];
			indexOfIpEnd++;
		}
		ip = ip + ": ";
		for (int i = indexOfIpEnd + 2; i < len; i++) {
			result = result + array[i];
		}
		return result;
	}
}
