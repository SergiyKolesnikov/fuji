

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



/**
 * 
 * Log the incoming message of client or server
 *
 */
public class Logger {
	private File outputFile;
	private BufferedWriter outputWriter;
	
	/**
	 * 
	 * Create a new Log and open the specific file
	 * 
	 * @param name
	 *          of the file to log the information (without .txt)
	 */
	public Logger(String name){
		outputFile = new File(name + ".txt");
		
		FileWriter fw;
		try {
			fw = new FileWriter(outputFile, true);	
			outputWriter = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 
	 * write a new line to log-File
	 * 
	 * @param line
	 *         to write in the File
	 */
	public void writeLogLine(String line){
		try {
			outputWriter.write(line + "\n");
			outputWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();                                                
		}
	}
	
	/**
	 * 
	 * close finally the outputWriter
	 * 
	 */
	public void finalizeLog()throws Exception{
		outputWriter.close();
	}
	
}