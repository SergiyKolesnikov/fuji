package chat.client; 

import java.io.BufferedWriter; 
import java.io.FileWriter; 
import java.sql.Date; 
import java.sql.Timestamp; 

/**
 * This class provides methods to log the chat history localy.
 * 
 * @author Marc Wiedenhöft
 * @version 1.0
 */
public  class  History {
	
	/**
	 * This method writes a given String into "history.log".<br>
	 * If the file already exists, the String is appended.<br> 
	 * 
	 * @param line - the String to log
	 */
	public static void append( String line )
	{
		try
		{
			FileWriter out = new FileWriter("history.log", true);
			BufferedWriter writer = new BufferedWriter(out);
			
			writer.write( 
					new Timestamp
					(
						new Date(System.currentTimeMillis()).getTime()
					).toString() +   
					line + 
					"\n");
			
			writer.flush();
			writer.close();
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}


}
