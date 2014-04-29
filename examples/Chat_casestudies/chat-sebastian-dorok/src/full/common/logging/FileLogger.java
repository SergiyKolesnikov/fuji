package common.logging; 

import java.io.BufferedWriter; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.text.SimpleDateFormat; 
import java.util.Date; 

/**
 * A logging factory for creating loggers.
 * 
 * @author Sebastian Dorok (sebastian.dorok@st.ovgu.de)
 * 
 */
public  class  FileLogger  implements ILogger {
	

	private String logFilename;

	

	public FileLogger(String logFilename) {
		this.logFilename = logFilename;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.logging.ILogger#log(java.lang.String)
	 */
	@Override
	public void log(String msg) {
		try {
			// Create file
			FileWriter fileStream = new FileWriter(this.logFilename, true);
			BufferedWriter out = new BufferedWriter(fileStream);
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
			out.write(format.format(new Date()) + " - " + msg + "\n");
			// Close the output stream
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
