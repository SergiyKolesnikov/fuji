package common;
import java.io.BufferedWriter;
import java.io.FileWriter;



public class HistoryWriter {

	private final String LOG_FILE;
	
	private BufferedWriter writer;
	
	public HistoryWriter(String name) {
		LOG_FILE = "log" + name + "_" + System.currentTimeMillis() + ".txt";
		try {
			writer = new BufferedWriter(new FileWriter(LOG_FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(String line) {
		try {
			writer.append(line);
			writer.newLine();
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}