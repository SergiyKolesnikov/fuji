


public class ROT13Encryption {

	private final static int SHIFTBY= 13;
	
	
	/*
	 * TODO:
	 * - handling for non-alphabetical chars
	 * - handling for "out of alphabetical-bounds"
	 */
	
	private static String caesarEncrypt(String s) {
		StringBuilder sb= new StringBuilder();
		for (int i=0; i< s.toCharArray().length; i++) {
			char c= s.toCharArray()[i];
			sb.append((char) (c+SHIFTBY));
		}
		return sb.toString();
	}
	
	private static String caesarDecrypt(String s) {
		StringBuilder sb= new StringBuilder();
		for (int i=0; i< s.toCharArray().length; i++) {
			char c= s.toCharArray()[i];
			sb.append((char) (c-SHIFTBY));
		}
		return sb.toString();
	}

	public static MessageObject encrypt(MessageObject msgo) {
		if (msgo.getMessage()==null) return msgo;
		MessageObject encryptedMsgo= new MessageObject(msgo.getMessageType(), msgo.getSender(), msgo.getRecipients(), msgo.getColor(), caesarEncrypt(msgo.getMessage()));
		return encryptedMsgo;
	}

	public static MessageObject decrypt(MessageObject msgo) {
		if (msgo.getMessage()==null) return msgo;
		MessageObject decryptedMsgo= new MessageObject(msgo.getMessageType(), msgo.getSender(), msgo.getRecipients(), msgo.getColor(), caesarDecrypt(msgo.getMessage()));
		return decryptedMsgo;
	}
}