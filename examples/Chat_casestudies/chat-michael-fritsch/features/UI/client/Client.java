package client;

import common.*;

public class Client {

	private static void feat_UI_init(String[] args, Client client) {
		// Feature: Gui
		feat_Gui_startGui("Chat " + args[0] + ":" + args[1], client);		
		
		// Feature: Console
		feat_Console_startConsole(client);
	};
	
	private static void feat_Gui_startGui(String title, Client client) {}
	private static void feat_Console_startConsole(Client client) {}
	
}
