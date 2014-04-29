package client;

import java.util.Scanner;

public class Console extends Thread {

	public Console() {
		super();
		start();
	}
	
	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		while (true) {
			String line = scan.nextLine();
			if (line == null || line.equals("/q")) {
				break;
			} else {
				Client.sendMessage(line);
			}
		}
		Client.close();
	}
	
	public void newChatLine(Message msg) {
		System.out.println(msg.getLine());
	}
	
}
