package client;

public class Client {
	public void send(String line) {
		if(line.contains("/username")){
			user = line.substring(10);
		}else{ 
			if(line.contains("/msg")){
				line = line.substring(5);
				int separator = line.indexOf(" ");
				String reciever = line.substring(0, separator);
				String message = line.substring(separator + 1);
				send(manager.send(reciever, message));
				}else{
					original(line);
					}
		}
		
	}
}