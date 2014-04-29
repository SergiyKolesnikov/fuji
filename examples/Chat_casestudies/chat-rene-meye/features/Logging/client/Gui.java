package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * TODO description
 */
public class Gui {
	boolean amLoadingAnLogFile;

	public void newChatLine(String line) {
		line = line.trim();
		if(!amLoadingAnLogFile)
			logLine(line);
		
		original(line);
	}
	
	public Gui(String title, Client chatClient) {
		loadLogFile();
	}
	
	private void logLine(String line){
		  try{
			  // Create file 
			  FileWriter fstream = new FileWriter(chatClient.username+".txt",true);
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(line+"\n");
			  //Close the output stream
			  out.close();
		  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		  }		
	}
	
	private void loadLogFile(){
		amLoadingAnLogFile = true;
		try{
			InputStream    fis= new FileInputStream(chatClient.username+".txt");
			BufferedReader br= new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
			String         line;
			while ((line = br.readLine()) != null) {
			 chatClient.gui.newChatLine(line);
			}
		}catch(Exception e){
			
		}finally{
	
		}
		amLoadingAnLogFile = false;
	}
}