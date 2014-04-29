package client;

import spamfilter.SPAMFilter;

/**
 * simple chat client
 */
public class Client implements Runnable, IClientProxy {

	private SPAMFilter spamFilter = new SPAMFilter();

	/**
	 * fire Listner method for the observer pattern
	 */
	public void fireAddLine(String line) {		
		boolean addLine = true;
		addLine = spamFilter.allowLine(line);
		
		if(!addLine)
			return;
		
		original(line);
	}
}
