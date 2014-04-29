package chat.server;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import chat.TextMessage;

public class Server 
{
	public void init(int port) throws IOException 
	{
		this.initSpamFilterList();
		
		original(port);
	}
	
	/**
	 * list of all the baaad words ;)
	 */
	private List<String> spamFilterList = new ArrayList<String>();
	
	/**
	 * send a message to all known connections
	 * 
	 * @param text
	 *            content of the message
	 */
	public void broadcast(TextMessage msg) 
	{
		msg = this.filterMessage(msg);
		
		original(msg);
	}
	
	private void initSpamFilterList()
	{
		this.spamFilterList.add("bitch");
		this.spamFilterList.add("asshole");
		this.spamFilterList.add("fuck");
		//...
	}
	
	private TextMessage filterMessage( TextMessage msg )
	{
		//decrypt the message text to filter it
		switch(msg.getCryptoType())
		{
			case 1:
			{
				msg.setContent(this.doSwapCrypto(msg.getContent()));
				break;
			}
			case 2:
			{
				msg.setContent(this.doROT13Crypto(msg.getContent()));
				break;
			}
		}
		
		for(String badWord : this.spamFilterList)
		{
			if(msg.getContent().contains(badWord))
			{
				System.out.println("Bad word detected: " + badWord + "...i will filter it...");
				msg.setContent(msg.getContent().replaceAll("(?:" + badWord + ")", "***"));
				System.out.println("The filtered message is: " + msg.getContent());
			}
		}
		
		//encrypt the message text
		switch(msg.getCryptoType())
		{
			case 1:
			{
				msg.setContent(this.doSwapCrypto(msg.getContent()));
				break;
			}
			case 2:
			{
				msg.setContent(this.doROT13Crypto(msg.getContent()));
				break;
			}
		}
				
		return msg;
	}
	
	/**
	 * Crypto a text message.
	 * This method encrypts normal text and decrypts an encrypted message.
	 */
	private String doSwapCrypto(String msg)
	{
		/**
		 * The text need to be at least 2 chars long for the change crypto.
		 * It swaps the first two letters and returns the result. 
		 */
		String first = String.valueOf(msg.charAt(0));
		String second = String.valueOf(msg.charAt(1));
			
		msg = second + first + msg.substring(2);
		
		return msg; 
	}	
	
	/**
	 * Crypto a text message.
	 * This method encrypts normal text and decrypts an encrypted message.
	 */
	private String doROT13Crypto(String msg)
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
