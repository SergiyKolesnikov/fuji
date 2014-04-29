package client; 

public  class  SpamFilter {
	
	
	private static final String[] spamWords = {"spam", "dummbatz"};

	

	public static void filter(Message msg) {
		String content = msg.getContent();
		for (int i = 0; i < spamWords.length; i++) {
			if (content.contains(spamWords[i])) {
				msg.setInvalid();
				break;
			}
		}
	}


}
