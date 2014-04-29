import java.io.FileWriter;
import java.io.IOException;

import javax.swing.text.SimpleAttributeSet;

/**
 * TODO description
 */
public class Gui {
	
	FileWriter writer;
	
	public void ShowStyledChatline(String line, SimpleAttributeSet attributes){		
		try {
			if (writer == null){
				writer = new FileWriter("chatLog" + System.currentTimeMillis() +".txt");
			}
			writer.append(line);
			writer.flush();
		} catch (IOException e) {
			System.out.println("Konnte in Datei nicht schreiben");
		}
		original(line, attributes);
	}

}