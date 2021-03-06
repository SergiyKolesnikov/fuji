import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.PrintStream; 
import java.util.Calendar; 

/**
 * A chat history logging class. Log is written to set file or to standard
 * output if no file was specified.
 */
public  class  History {
	
	
	public static History instance = new History();

	

	private FileOutputStream logFileStream = null;

	
	private PrintStream logFilePrintStream = null;

	

	public History() {
	}

	

	public void setFile(File logFile) {
		if (logFileStream != null) {
			try {
				logFileStream.close();
			} catch (IOException e) {
				System.out.println("WARNING: Can not close previous log file.");
			}
		}

		try {
			logFileStream = new FileOutputStream(logFile);
			logFilePrintStream = new PrintStream(logFileStream);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to open log file.");
			e.printStackTrace();
		}
	}

	

	public PrintStream out() {
		if (logFilePrintStream == null)
			logFilePrintStream = System.out;

		return logFilePrintStream;
	}

	

	public void logTextMessage(TextMessage msg) {
		StringBuffer sb = new StringBuffer();
		sb.append(Calendar.getInstance().getTime().toString());
		sb.append("  ");
		
		sb.append("CONTENT: ");
		sb.append(msg.getContent());
		
		out().println(sb.toString());
	}


}
