package spamfilter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SPAMFilter {

	Map<Integer, String> blackList;
	String blackListFilePath = "blacklist.txt";
	
	public SPAMFilter() {
		try {
			List<String> list = IORoutines.loadStrings(new File(blackListFilePath));
			blackList = new HashMap<Integer, String>();
			for (String string : list)
				blackList.put(string.hashCode(), string);
		} catch (IOException e) {
		}
	}
	
	public boolean allowLine(String line) {
		line = line.toLowerCase();
		String[] words = line.split(" ");
		
		boolean forbidden = false;
		for (int i = 0; i < words.length; i++) {
			if(isInBlackList(words[i])) {
				forbidden = true;
				break;
			}
		}
		
		return !forbidden;
	}
	
	private boolean isInBlackList(String word) {
		if(blackList == null)
			return false;
		
		return (blackList.get(word.trim().hashCode()) != null);
	}
}
