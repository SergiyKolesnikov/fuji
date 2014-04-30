package common;

abstract class Crypter$$Crypter$common {

	public Crypter$$Crypter$common() {
	}
	
	public TextMessage encrypt(TextMessage message) {
		return message;
	}
	
	public TextMessage decrypt(TextMessage message) {
		return message;
	}
}

abstract class Crypter$$Cryptreverse$common extends  Crypter$$Crypter$common  {
	
	public TextMessage encrypt(TextMessage message) {
		String text = message.getContent();
		String text2 = "";
		for (int i = 0;i < text.length();i++)
			text2 += text.charAt(text.length()-i-1);
		return new TextMessage(text2);
	}

	public TextMessage decrypt(TextMessage message) {
		String text = message.getContent();
		String text2 = "";
		for (int i = 0;i < text.length();i++)
			text2 += text.charAt(text.length()-i-1);
		return new TextMessage(text2);
	}
      // inherited constructors



	public Crypter$$Cryptreverse$common (  ) { super(); }
}

public class Crypter extends  Crypter$$Cryptreverse$common  {
	
	public TextMessage encrypt(TextMessage message) {
		String text = message.getContent();
		String text2 = "";
		for (int i = 0;i < text.length();i++)
			text2 += (char) (text.charAt(i) + 100);
		return new TextMessage(text2);
	}

	public TextMessage decrypt(TextMessage message) {
		String text = message.getContent();
		String text2 = "";
		for (int i = 0;i < text.length();i++)
			text2 += (char) (text.charAt(i) - 100);
		return new TextMessage(text2);
	}
      // inherited constructors



	public Crypter (  ) { super(); }
}