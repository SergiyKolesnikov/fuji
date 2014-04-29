import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class Connection {
	private File serverlogfile;
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;
	
	public Connection(Socket s, Server server) {
		try {
			this.serverlogfile = new File("serverlog_"+ System.currentTimeMillis() + ".txt");
			this.fileWriter = new FileWriter(serverlogfile);
			this.bufferedWriter = new BufferedWriter(fileWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
			
	private void handleIncomingMessage(String host, Object msg) {
		if (msg instanceof TextMessage) {
			log(host, (TextMessage) msg);
		}
		original(host, msg);
	}
	
	private void log(String name, TextMessage msg) {	
		try {
			bufferedWriter.append(name + " @ " + new Date() + " : " + msg.getContent());
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}