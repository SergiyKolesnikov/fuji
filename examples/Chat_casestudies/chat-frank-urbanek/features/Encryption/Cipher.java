
public interface Cipher {
	
	String encode(String plain);
	
	String decode(String cipher);
	
	public enum Codec {
	   ROT13, SWITCH, NONE
	  }
}
