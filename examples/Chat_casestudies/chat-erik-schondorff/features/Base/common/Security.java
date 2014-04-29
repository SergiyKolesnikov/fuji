package common;

public class Security
{
	private static int getLetter(int Original, boolean Positive)
	{
		int offset = (Positive) ? 13 : -13;
		int buffer;

		if ((Original >= 65) && (Original <= 90))
		{
			if (Positive)
			{
				if (Original > 77)
					return (char) (65 + (13 - (90 - Original)));
				else
					return (char) (Original + 13);
			}
			else
			{
				if (Original < 78)
					return (char) (90 - (13 - (Original - 65)));
				else
					return (char) (Original - 13);
			}

		}
		else if ((Original >= 97) && (Original <= 122))
		{
			if (Positive)
			{
				if (Original > 109)
					return (char) (97 + (13 - (122 - Original)));
				else
					return (char) (Original + 13);
			}
			else
			{
				if (Original < 110)
					return (char) (122 - (13 - (Original - 97)));
				else
					return (char) (Original - 13);
			}
		}
		else
			return Original;
	}

	public static String Encode(String Text)
	{
		String encoded = "";
		for (int i = 0; i < Text.length(); i += 2)
		{
			int nextChar;
			if (i + 1 < Text.length())
			{
				nextChar = getLetter(Text.charAt(i + 1), true);
				encoded += String.valueOf((char) nextChar);
			}

			nextChar = getLetter(Text.charAt(i), true);
			encoded += String.valueOf((char) nextChar);
		}
		return encoded;
	}

	public static String Decode(String Text)
	{
		String decoded = "";
		for (int i = 0; i < Text.length(); i += 2)
		{
			int nextChar;
			if (i + 1 < Text.length())
			{
				nextChar = getLetter(Text.charAt(i + 1), false);
				decoded += String.valueOf((char) nextChar);
			}

			nextChar = getLetter(Text.charAt(i), false);
			decoded += String.valueOf((char) nextChar);
		}
		return decoded;
	}

}