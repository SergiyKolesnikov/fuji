


public class InvertEncryption {

	private static String invert(String s) {
		if (s==null) return null;
		StringBuilder sb= new StringBuilder();
		for (int i=0; i<s.toCharArray().length; i++) {
			char c= s.toCharArray()[i];
			sb.insert(0,c);
		}
		return sb.toString();
	}


	public static MessageObject encrypt(MessageObject msgo) {
		if (msgo.getMessage()==null) return msgo;
		return new MessageObject(msgo.getMessageType(),msgo.getSender(),msgo.getRecipients(),msgo.getColor(), invert(msgo.getMessage()));
	}


	public static MessageObject decrypt(MessageObject msgo) {
		if (msgo.getMessage()==null) return msgo;
		return new MessageObject(msgo.getMessageType(),msgo.getSender(),msgo.getRecipients(),msgo.getColor(), invert(msgo.getMessage()));
	}
}