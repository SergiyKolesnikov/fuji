package common;

/**
 * encrypts and decryptes text messages 
 * @author Mikka
 *
 */
public class Encrypter {
	
	public static String encryptROT13(String msg) {
		String encryptedContent = "";
		char c; 
		for(int i = 0; i < msg.length(); i++) {
			c = msg.charAt(i);
			if (c >= 65 && c <= 90) {
				if (c < 78) {
					c += 13;
				} else {
					c -= 13;
				}
			}else if (c >= 97 && c <= 122) {
				if (c < 110) {
					c += 13;
				} else {
					c -= 13;
				}
			}
			encryptedContent += c;
		}
		return encryptedContent;
	}
	
	public static String encryptSwapFirstCharacters(String msg) {
		String value = "";
		String[] content = msg.split(" "); 
		for(String part : content) {
			if (part.length() == 2) {
				part = part.substring(1, 2) + part.substring(0, 1);
			} else if (part.length() > 2) {
				part = part.substring(1, 2) + part.substring(0, 1) + part.substring(2);
			}
			value += part + " ";
		}
		return value.trim();
	}
	
	public static String decrypt(ITextMessage msg) {
		if (msg instanceof ROT13EncryptedTextMessage)
			return decrypt((ROT13EncryptedTextMessage)msg);
		if (msg instanceof SwappedEncryptedTextMessage)
			return decrypt((SwappedEncryptedTextMessage)msg);
		return null;
	}
	
	private static String decrypt(ROT13EncryptedTextMessage msg) {
		String value = msg.getAuthor() + " - ";
		char c; 
		for(int i = 0; i < msg.getContent().length(); i++) {
			c = msg.getContent().charAt(i);
			if (c >= 65 && c <= 90) {
				if (c < 78) {
					c += 13;
				} else {
					c -= 13;
				}
			}else if (c >= 97 && c <= 122) {
				if (c < 110) {
					c += 13;
				} else {
					c -= 13;
				}
			}
			value += c;
		}
		
		// zu anschauungszwecken
		value = String.format("%s (Originalnachricht: %s)", value, msg.getContent());
		return value;
	}

	private static String decrypt(SwappedEncryptedTextMessage msg) {
		String value = msg.getAuthor() + " - ";
		String[] content = msg.getContent().split(" "); 
		for(String part : content) {
			if (part.length() == 2) {
				part = part.substring(1, 2) + part.substring(0, 1);
			} else if (part.length() > 2) {
				part = part.substring(1, 2) + part.substring(0, 1) + part.substring(2);
			}
			value += part + " ";
		}
		
		// zu anschauungszwecken
		value = String.format("%s (Originalnachricht: %s)", value, msg.getContent());
		return value.trim();
	}
}
