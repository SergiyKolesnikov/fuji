public class Connection extends Thread {
	protected String username = "/username";
	protected String usernameC1 = "47$117$115$101$114$110$97$109$101$";
	protected String usernameC2 = "emanresu/";
	protected String usernameC3 = "101$109$97$110$114$101$115$117$47$";
	
	protected String msg = "/msg";
	protected String msgC1 = "47$109$115$103$";
	protected String msgC2 = "gsm/";
	protected String msgC3 = "103$115$109$47$";
	
	protected String command1;
	protected String command2;

	private void sendMsg(String line) {
		String[] messageArray;
		int decType = getDecryptionType(line);
		messageArray = setCommands(decType, line);
		
		switch (decType) {
		case 0:
			handleNoEncoding(messageArray, line);
			break;
		case 1:
			handleOnlyAscii(messageArray, line);		
			break;
		case 2:
			handleOnlyReverse(messageArray, line);
			break;
		default:
			handleBothEncrypts(messageArray, line);
			break;
		}
	}
	
	private void handleBothEncrypts(String[] messageArray, String line) {
		if (messageArray[messageArray.length-1].equals(command1)) {
			clientName = reverse(deAscii(messageArray[messageArray.length-2]));
		} else if (messageArray[messageArray.length-1].equals(command2)) {
			String message = "";
			for (int i = messageArray.length-3; i >= 0; i--) {
				message = message + messageArray[i];
			}
			server.pn(reverse(deAscii(messageArray[messageArray.length-2])), message);
		} else {			
			broadcast(line);
		}
	}
	
	private void handleOnlyReverse(String[] messageArray, String line) {
		if (messageArray[messageArray.length-1].equals(command1)) {
			clientName = reverse(messageArray[messageArray.length-2]);
		} else if (messageArray[messageArray.length-1].equals(command2)) {
			String message = "";
			for (int i = messageArray.length-3; i >= 0; i--) {
				message = message + messageArray[i];
			}
			server.pn(reverse(messageArray[messageArray.length-2]), message);
		} else {			
			broadcast(line);
		}
	}
	
	private void handleOnlyAscii(String[] messageArray, String line) {
		if (messageArray[0].equals(command1)) {
			clientName = deAscii(messageArray[1]);
		} else if (messageArray[0].equals(command2)) {
			String message = "";
			for (int i = 2; i < messageArray.length; i++) {
				message = message + messageArray[i];
			}
			server.pn(deAscii(messageArray[1]), message);
		} else {			
			broadcast(line);
		}
	}
	
	private void handleNoEncoding(String[] messageArray, String line) {
		if (messageArray[0].equals(command1)) {
			clientName = messageArray[1];
		} else if (messageArray[0].equals(command2)) {
			String message = "";
			for (int i = 2; i < messageArray.length; i++) {
				message = message + messageArray[i];
			}
			server.pn(messageArray[1], message);
		} else {			
			broadcast(line);
		}
	}
	
	private String[] setCommands(int decType, String line) {
		String[] messageArray;
		switch (decType) {
			case 0: {
				messageArray = line.split(" ");
				command1 = username;
				command2 = msg;
				break;
			}
			case 1: {
				messageArray = line.split("32\\$");
				command1 = usernameC1;
				command2 = msgC1;
				break;
			}
			case 2: {
				messageArray = line.split(" ");
				command1 = usernameC2;
				command2 = msgC2;
				break;
			}
			default: {
				messageArray = line.split("32\\$");
				command1 = usernameC3 + "§";
				command2 = msgC3 + "§";
				break;
			}
		}
		return messageArray;
	}
	
	private int getDecryptionType(String line) {
		int result;
		String[] messageArray = line.split(" ");
		String[] messageArrayC = line.split("32\\$");
		
		if (messageArray.length > 1) {
			if (messageArray[0].startsWith("/")) {				
				result = 0;
			} else {
				result = 2;
			}
		} else {
			if(messageArrayC[0].startsWith("47")) {
				result = 1;
			} else {
				result = 3;
			}
		}
		return result;
	}
	
	private String deAscii(String target) {
		String result = "";
		String tmp = "";
		int len = target.length();
		int ascii = 0;
		char[] array = new char[len];
		target.getChars(0, len, array, 0);
		for (int i = 0; i < len; i++) {
			if (array[i] != '§') {
				if (array[i] != '$') {
					tmp = tmp + array[i];
				} else {
					ascii = Integer.parseInt(tmp);
					tmp = "";
					result = result + (char) ascii;
				}
			}
		}
		return result;
	}
	
	private String reverse(String target) {
		String result = "";
		int limit = 0;
		int len = target.length();
		char[] array = new char[len];
		target.getChars(0, len, array, 0);
		for (int i = len - 1; i >= limit; i--) {
			result = result + array[i];
		}
		return result;
	}

}