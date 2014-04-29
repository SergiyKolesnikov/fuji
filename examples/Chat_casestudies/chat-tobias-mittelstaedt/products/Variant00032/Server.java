package server; 

import java.io.IOException; 
import java.util.logging.FileHandler; 
import java.util.logging.Handler; 
import java.util.logging.Logger; 
import java.util.logging.SimpleFormatter; 

import java.util.Iterator; 

import server.Connection; 

public   class  Server {
	
	
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

	

	public void broadcast  (String text) {

		if (text.contains(badWords[0]) || text.contains(badWords[1])
				|| text.contains(badWords[2])) {
			synchronized (connections) {
				for (Iterator iterator = connections.iterator(); iterator
						.hasNext();) {
					Connection connection = (Connection) iterator.next();

					connection.send(text);

				}
			}
		}

	}

	

	private String[] badWords = new String[3];

	

	public Server() {
		badWords[0] = "test";
		badWords[1] = "evil";
		badWords[2] = "foobar";
	}


}
