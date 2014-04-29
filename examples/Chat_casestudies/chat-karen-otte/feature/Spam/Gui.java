import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.SimpleAttributeSet;

public class Gui {
	
	public List<String> badWords;
	
	public void initSpamwords() {
		badWords = new ArrayList<String>();
		badWords.add("bad");
	}

	public void ShowStyledChatline(String line, SimpleAttributeSet attributes){		
		if (badWords == null){
			initSpamwords();
		}
		
		for(String s : badWords){
			if(line.toLowerCase().contains(s.toLowerCase())){
				System.out.println("Watch your language!!");
				line = "Watch your language!!";
			}
		}
		original(line, attributes);
	}

}