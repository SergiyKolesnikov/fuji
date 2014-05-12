package Base;

import java.io.IOException;
import java.io.FileWriter;



public class Logging {
	protected FileWriter fw;
	public Logging(){
	}
	public void log(Object msg){
		if(msg instanceof TextMessage){
			try{
				((Logging) this).fw = new FileWriter("chatlog.txt",true);
				((Logging) this).fw.write( ((TextMessage) msg).getContent()+"\r\n");
				((Logging) this).fw.close();
			}
			catch (IOException ex){
				System.out.println("Schreibfehler beim loggen!");
			}
		}
	}
}