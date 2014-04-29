package common.encryption;

public class SimpleEncrypter implements Encrypter {

	@Override
	public String encrypt(String input) {
		if(input.length()<=1)return input;
		String firstChar=input.substring(0,1);
		String secondChar=input.substring(1, 2);
		
		return secondChar+firstChar+input.substring(2);
	}

	@Override
	public String decrypt(String output) {
		return encrypt(output);
	}

}
