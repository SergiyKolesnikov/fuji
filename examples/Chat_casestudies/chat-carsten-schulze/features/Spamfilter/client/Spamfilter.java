package client;

import common.Message;
import java.util.List;
import java.util.TreeSet;

public class Spamfilter implements ChatPlugin {

	private byte type = 0x04;
	private TreeSet<String> blacklist;  // blacklist of Regular Expressions 
	
	Spamfilter (List<String> slist ,Client client) {
		this.blacklist = new TreeSet<String>(slist);
		client.loadPlugin(this);
		System.out.println("Spamfilterplugin loaded.");
	}
	
	@Override
	public byte getType() {
		return type;
	}

	@Override
	public Message process(Message msg) {
		for (String regex : blacklist){
			if (msg.getText().matches(regex))
				return null;
		}
		return msg;
	}
	
	public void add (String regex){
		blacklist.add(regex);
	}
	public void remove (String regex){
		blacklist.remove(regex);
	}

}
