package model; public   class  Crypto {
	
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

	
	
	private String doReverse  (String toReverse) {
		return reverse(toReverse);
	}

	
	
	private String doAscii  (String toAscii) {
		return ascii(toAscii);
	}

	

	private String doDeAscii  (String toDeAscii) {
		return deAscii(toDeAscii);
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

	
	
	private String ascii(String target) {
		String result = "";
		int len = target.length();
		char[] array = new char[len];
		target.getChars(0, len, array, 0);
		for (int i = 0; i < len; i++) {
			result = result + (int) array[i] + "$";
		}
		result = result + "�";
		return result;
	}

	
	
	private String deAscii(String target) {
		String result = "";
		String tmp = "";
		int len = target.length();
		int ascii = 0;
		char[] array = new char[len];
		target.getChars(0, len, array, 0);
		for (int i = 0; i < len; i++) {
			if (array[i] != '�') {
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
