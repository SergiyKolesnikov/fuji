package server;

import java.util.ArrayList;

import common.TextMessage;


public class SpamfilterPlugin {

	private ArrayList<String> spamList = new ArrayList<String>();

	public SpamfilterPlugin() {
		initSpamlist();
	}

	public void handleIncomingMessage(TextMessage msg) {
		for (String spam : spamList) {
			if (msg.getContent().contains(spam)) {
				msg.setContent("Ich habe schlimme Worter benutzt!!!");
			}
		}
	}

	private void initSpamlist() {
		spamList.add("arsch");
		spamList.add("idiot");
	}

}
