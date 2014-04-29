package client;

public class Rot13 {
	
	public static String encode(String text) {
		char[] letters = text.toCharArray();
		for (int i = 0; i < letters.length; i++) {
			letters[i] = (char) (letters[i] + 13);
		}
		return String.valueOf(letters);
	}

	public static void decode(Message msg) {
		if (msg.isEncoded()) {
			char[] letters = msg.getContent().toCharArray();
			for (int i = 0; i < letters.length; i++) {
				letters[i] = (char) (letters[i] - 13);
			}
			msg.setContent(String.valueOf(letters));
			msg.setEncoded(false);
		}
	}
}
