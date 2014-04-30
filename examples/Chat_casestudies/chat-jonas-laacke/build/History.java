

import java.io.FileWriter;
import java.io.IOException;



public class History {
	
	public static void saveMessage(String message){

		try {
			FileWriter writer=new FileWriter("log.txt",true);
			writer.write(message+"\r\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}