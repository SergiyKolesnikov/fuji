
import java.io.IOException;
import java.util.Scanner;



public class Console implements ChatLineListener{

	private Client client;

	
	public void newChatLine(String line){
		System.out.println(line);
	}
	
	public Console(Client client) throws IOException{
		this.client = client;
		client.addListener(this);
		startConsole();
	}
	
	private void startConsole() throws IOException{
		while(true){
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			client.send(input);
		}
	}
	
}