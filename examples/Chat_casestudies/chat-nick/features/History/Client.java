import java.io.IOException;
import java.util.Date;


public class Client {
	private File clientlogfile;
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;	
	
	public Client(String host, int port) {
		try {
			clientlogfile = new File("clientlog_" + System.currentTimeMillis()+ ".txt");
			fileWriter = new FileWriter(clientlogfile);
			bufferedWriter = new BufferedWriter(fileWriter);		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void handleTextMessage(TextMessage msg) {
		log(msg);
		original(msg);
	}
	
	private void log(TextMessage msg) {	
		try {
			bufferedWriter.append(new Date() + " : " + msg.getContent());
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}