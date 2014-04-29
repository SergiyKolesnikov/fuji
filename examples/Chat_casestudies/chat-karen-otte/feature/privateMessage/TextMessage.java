import java.net.InetAddress;

/**
 * TODO description
 */
public class TextMessage {
	
	public boolean isPrivate;
	public String sender;
	public String receiverName;
	
	public String getMessage(){
		String line = content;
		int indexFromName = 5;
		line = line.trim();
		if (line.indexOf(' ') != -1){
			line = line.substring(indexFromName, line.length());
			line = line.substring(line.indexOf(' ') +1);
		}
		return line;
	}
}