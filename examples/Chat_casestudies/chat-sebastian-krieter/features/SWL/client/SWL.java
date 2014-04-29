package client;

public class SWL {
	
	public static String encode(String text) {
		return swl(text);
	}

	public static void decode(Message msg) {
		if (msg.isEncoded()) {
			msg.setContent(swl(msg.getContent()));
			msg.setEncoded(false);
		}
	}
	
	private static String swl(String s) {
		if (s.length() >= 2) {
			char[] letters = s.toCharArray();
			
			char first = letters[0];
			letters[0] = letters[1];
			letters[1] = first;
			
			return String.valueOf(letters);
		}
		return s;
	}

}
