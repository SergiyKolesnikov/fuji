package encryption;

public class ROT13 implements Encryption
{
	public String encode(String text) 
	{
		return getShiftedText(text, -13);
	}

	public String decode(String text) 
	{
		return getShiftedText(text, 13);
	}
	
	private String getShiftedText(String text, int shift)
	{
		if (text == null) return text;

		StringBuilder shiftedText = new StringBuilder();
		for (int i = 0; i < text.length(); i++) 
		{
			char c = text.charAt(i);
			c += shift;
			shiftedText.append(c);
		}
		return shiftedText.toString();

	}
	
	
}
