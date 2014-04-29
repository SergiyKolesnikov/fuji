package common;
/* 
 * Klasse zum En/Decodieren von Base64-Strings, da sun.misc.* bekanntlich fragwürdig ist und 
 * alles andere zusätzliche Packages erfordert.
 * 
 * Quelle: http://www.bennyn.de/programmierung/java/base64-kodieren-und-enkodieren-in-java.html
 */

public class Base64 
{
	private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
	private static int[] toInt = new int[128];

	static
	{
		for (int i = 0; i < ALPHABET.length; i++)
		{
			toInt[ALPHABET[i]] = i;
		}
	}

	public static String encode(String string)
	{
		byte[] buf = string.getBytes();
		int size = buf.length;
		char[] ar = new char[((size + 2) / 3) * 4];
		int a = 0;
		int i = 0;
		while (i < size)
		{
			byte b0 = buf[i++];
			byte b1 = (i < size) ? buf[i++] : 0;
			byte b2 = (i < size) ? buf[i++] : 0;

			int mask = 0x3F;
			ar[a++] = ALPHABET[(b0 >> 2) & mask];
			ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
			ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
			ar[a++] = ALPHABET[b2 & mask];
		}
		switch (size % 3)
		{
		case 1:
			ar[--a] = '=';
		case 2:
			ar[--a] = '=';
		}
		return new String(ar);
	}

	public static String decode(String base64)
	{
		int delta = base64.endsWith("==") ? 2 : base64.endsWith("=") ? 1 : 0;
		byte[] buffer = new byte[base64.length() * 3 / 4 - delta];
		int mask = 0xFF;
		int index = 0;
		for (int i = 0; i < base64.length(); i += 4)
		{
			int c0 = toInt[base64.charAt(i)];
			int c1 = toInt[base64.charAt(i + 1)];
			buffer[index++] = (byte) (((c0 << 2) | (c1 >> 4)) & mask);
			if (index >= buffer.length)
			{
				return new String(buffer);
			}
			int c2 = toInt[base64.charAt(i + 2)];
			buffer[index++] = (byte) (((c1 << 4) | (c2 >> 2)) & mask);
			if (index >= buffer.length)
			{
				return new String(buffer);
			}
			int c3 = toInt[base64.charAt(i + 3)];
			buffer[index++] = (byte) (((c2 << 6) | c3) & mask);
		}
		return new String(buffer);
	}
}