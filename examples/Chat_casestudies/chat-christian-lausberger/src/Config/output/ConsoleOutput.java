package output; 

import client.IChatLineListener; 

public  class  ConsoleOutput  implements IChatLineListener {
	

	public void newChatLine(String line) {
		System.out.print(line);
	}

	

	public void stop() {
		}


}
