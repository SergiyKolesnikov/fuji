package client;

import java.util.ArrayList;

import common.AuthenticationMessage;

/**
 * TODO description
 */
public class Client {
	private String password;
	
	private void initStuff(String host, int port, String[] args){
		if(args.length < 4){
			System.out.println("Error: You forgot the Username and Password! \n \t Syntax: ChatClient <host> <port> (<username> <password>)");
			System.exit(1);
		}else{
			this.username = args[2];
			this.password = args[3];
		}
		original(host, port, args);
	}
	
	public void run() {	
		this.send(new AuthenticationMessage(username, password));
		original();
	}
}