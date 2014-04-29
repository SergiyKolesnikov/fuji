package encryption;

public class NoEncryption extends Encryption {

	public String encode(String text) {
		return text;
	}

	public String decode(String text) {
		return text;
	}

}
