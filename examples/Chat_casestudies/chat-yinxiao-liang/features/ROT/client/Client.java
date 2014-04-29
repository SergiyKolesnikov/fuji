package client;

/**
 * TODO description
 */
public class Client {
	public void send(String line)
	{
		String Send;
		 StringBuffer tempReturn = new StringBuffer();

		 	int abyte = 0;
		 	boolean flag = true;
	        for (int i=0; i<line.length(); i++) {            
	        	abyte = line.charAt(i);
            if(abyte != '<'&&flag)
            {
            int cap = abyte & 32;
            abyte &= ~cap;
            abyte = ( (abyte >= 'A') && (abyte <= 'Z') ? ((abyte - 'A' + 13) % 26 + 'A') : abyte) | cap;
            }
            else
            	{if(abyte =='>')
            		flag = true;
            	else
            	flag = false;
            	}
            tempReturn.append((char)abyte);
            }
	        Send = tempReturn.toString();
	        original (Send);
}
}