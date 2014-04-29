package client;

import java.util.List;

import common.TextMessage;

/**
 * simple chat client
 */
public class Client implements Runnable {

	private final List<String> restrictedList = createRestrictedList();

    private List<String> createRestrictedList()
    {
    	List<String> result = new ArrayList<String>();
        result.add("spam");
        return result;
    }
    	
	public void send(String line) 
	{
		if (!isSpam(line)) original(line);
	}
	
	private boolean isSpam(String msg) 
	{
		String content = msg.toLowerCase();
		
		for (String restrictedWord : restrictedList) 
		{
			if (content.contains(restrictedWord.toLowerCase())) 
			{
				return true;
			}
		}
		return false;
	}
}
