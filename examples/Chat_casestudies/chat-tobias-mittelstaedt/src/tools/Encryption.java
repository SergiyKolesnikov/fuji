package tools;

import common.TextMessage;

public class Encryption {

	public static final String NONE = "None";
	public static final String ROT13 = "ROT13";
	public static final String REVERSAL = "Reversal";

	public static String rot13(TextMessage message) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < message.getContent().length(); i++) {
			char c = message.getContent().charAt(i);
			if (c >= 'a' && c <= 'm')
				c += 13;
			else if (c >= 'A' && c <= 'M')
				c += 13;
			else if (c >= 'n' && c <= 'z')
				c -= 13;
			else if (c >= 'N' && c <= 'Z')
				c -= 13;
			sb.append(c);
		}

		return sb.toString();

	}

	public static String reverseMessage(TextMessage message) {

		return new StringBuffer(message.getContent()).reverse().toString();
	}



}
