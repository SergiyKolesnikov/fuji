package swpl.chat.common;
import java.util.LinkedList;
import java.util.List;



abstract class DefaultEncryption$$Encryption$swpl$chat$common implements Encryption {
	
	public DefaultEncryption$$Encryption$swpl$chat$common() {
	}
	
	public String encode(String msg) {
		return msg;
	}

	public String decode(String msg) {
		return msg;
	}

}



abstract class DefaultEncryption$$ROT13Encryption$swpl$chat$common extends  DefaultEncryption$$Encryption$swpl$chat$common  {
	
	public String encode(String msg) {
		return super.encode(rotEncode(msg));
	}

	public String decode(String msg) {
		return rotDecode(super.decode(msg));
	}

	private String rotEncode(String msg) {
		/** Rot13 implementation borrowed from
			 * http://sanjaal.com/java/64/java-encryption/java-implementation-rot-13-encoding-algorithm/
			 */
        String encodedText = "";
        int textLength = msg.length();
 
        for (int i = 0; i < textLength; i++) {
            char currentChar = msg.charAt(i);
            if ((currentChar >= 65 && currentChar <= 77)
                    || (currentChar >= 97 && currentChar <= 109)) {
                encodedText += (char) (currentChar + 13);
            } else if ((currentChar >= 78 && currentChar <= 90)
                    || (currentChar >= 110 && currentChar <= 122)) {
                encodedText += (char) (currentChar - 13);
            } else {
                encodedText += currentChar;
            }
        }
        return encodedText;
	}

	private String rotDecode(String msg) {
		return rotEncode(msg);
	}
      // inherited constructors


	
	public DefaultEncryption$$ROT13Encryption$swpl$chat$common (  ) { super(); }
}


public class DefaultEncryption extends  DefaultEncryption$$ROT13Encryption$swpl$chat$common  {
	
	public String encode(String msg) {
		return super.encode(swapEncode(msg));
	}

	public String decode(String msg) {
		return swapDecode(super.decode(msg));
	}

	private String swapEncode(String msg) {
		if (msg.length() >= 2) {
			char firstChar = msg.charAt(0);
			char sndChar = msg.charAt(1);
			char[] prefix = {sndChar, firstChar};
			
			String encodedMsg = new String(prefix);
			if (msg.length() > 2) {
				encodedMsg += msg.substring(2);
			}
			
			return encodedMsg;
		} else {
			return msg;
		}
	}

	private String swapDecode(String msg) {
		return swapEncode(msg);
	}
      // inherited constructors


	
	public DefaultEncryption (  ) { super(); }
}