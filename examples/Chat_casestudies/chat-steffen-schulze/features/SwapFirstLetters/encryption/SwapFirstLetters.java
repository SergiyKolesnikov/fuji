package encryption;

public class SwapFirstLetters implements Encryption{

	public String decode(String text) 
	{
		return swap(text);
	}

	public String encode(String text) 
	{
		return swap(text);
	}
	
	private String swap(String text)
	{
		if ((text == null) || (text.length() < 2)) return text;
		
		char[] c = text.toCharArray();

		char temp = c[0];
		c[0] = c[1];
		c[1] = temp;

		return new String(c);
	}

}
