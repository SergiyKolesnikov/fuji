package chat;

/**
 * This class provides methods to encrypt or decrypt a String in 2 different ways:<br>
 * - Swap the first 2 letters<br>
 * - ROT13 En/Decryption<br>
 * 
 * @author Marc Wiedenhöft
 * @version 1.0
 */
public class Crypto 
{
	/**
	 * Crypto a text message.
	 * This method encrypts normal text and decrypts an encrypted message.
	 */
	public static String doROT13Crypto(String msg)
	{
		/**
		 * The text need to be at least 1 char long for the ROT13 crypto.
		 * It swaps the first 13 letters of the alphabet with the
		 * corresponding last 13 and vice versa.
		 */
		String newMsg = "";
		int length = msg.length();
		for(int i = 0; i < length; i++)
		{
			String letter = String.valueOf( msg.charAt(i) );
				
			if(letter.equals("a")) { newMsg += "n"; continue; }
			if(letter.equals("b")) { newMsg += "o"; continue; }
			if(letter.equals("c")) { newMsg += "p"; continue; }
			if(letter.equals("d")) { newMsg += "q"; continue; }
			if(letter.equals("e")) { newMsg += "r"; continue; }
			if(letter.equals("f")) { newMsg += "s"; continue; }
			if(letter.equals("g")) { newMsg += "t"; continue; }
			if(letter.equals("h")) { newMsg += "u"; continue; }
			if(letter.equals("i")) { newMsg += "v"; continue; }
			if(letter.equals("j")) { newMsg += "w"; continue; }
			if(letter.equals("k")) { newMsg += "x"; continue; }
			if(letter.equals("l")) { newMsg += "y"; continue; }
			if(letter.equals("m")) { newMsg += "z"; continue; }
			if(letter.equals("n")) { newMsg += "a"; continue; }
			if(letter.equals("o")) { newMsg += "b"; continue; }
			if(letter.equals("p")) { newMsg += "c"; continue; }
			if(letter.equals("q")) { newMsg += "d"; continue; }
			if(letter.equals("r")) { newMsg += "e"; continue; }
			if(letter.equals("s")) { newMsg += "f"; continue; }
			if(letter.equals("t")) { newMsg += "g"; continue; }
			if(letter.equals("u")) { newMsg += "h"; continue; }
			if(letter.equals("v")) { newMsg += "i"; continue; }
			if(letter.equals("w")) { newMsg += "j"; continue; }
			if(letter.equals("x")) { newMsg += "k"; continue; }
			if(letter.equals("y")) { newMsg += "l"; continue; }
			if(letter.equals("z")) { newMsg += "m"; continue; }
				
			newMsg += letter;
		}
		
		return newMsg; 
	}
}
