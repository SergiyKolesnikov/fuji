package common.encryption;

public class Rot13Encrypter implements Encrypter {

	@Override
	public String encrypt(String input) {
		StringBuffer output = new StringBuffer();
			for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c >= 'a' && c <= 'm')
				c += 13;
			else if (c >= 'n' && c <= 'z')
				c -= 13;
			else if (c >= 'A' && c <= 'M')
				c += 13;
			else if (c >= 'A' && c <= 'Z')
				c -= 13;
			output.append(c);
		}
		return output.toString();
	}

	@Override
	public String decrypt(String output) {
		return encrypt(output);
	}

}
