package server; 

import java.io.IOException; 
import java.util.logging.FileHandler; 
import java.util.logging.Handler; 
import java.util.logging.Logger; 
import java.util.logging.SimpleFormatter; 

public  class  Server {
	
	
	public static void main(String args[]) throws IOException {
		if (args.length > 2) {
			throw new RuntimeException("Syntax: ChatServer <port> "
					+ "{logfile}");

		} else if (args.length == 2) {
			new Server(Integer.parseInt(args[0]), args[1]);

		} else if (args.length == 1) {
			new Server(Integer.parseInt(args[0]), null);
		}
	}

	
	
	protected final Logger log = Logger.getLogger(Server.class.getName());

	

	public Server(String logPath) {
		Handler handler;
		try {
			handler = new FileHandler(logPath);
			handler.setFormatter(new SimpleFormatter());
			log.addHandler(handler);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	

	public void broadcast(String text) {
		log.info(text);
		original(text);

	}


}
