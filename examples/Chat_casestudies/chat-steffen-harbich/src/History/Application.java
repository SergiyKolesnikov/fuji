import java.io.File;
import java.io.IOException;

public class Application {

	public static void startServer(String[] args) {
		History.instance.setFile(new File("server.log"));
		original(args);
	}
	
	public static void startClient(String[] args) {
		if (args.length >= 3)
			History.instance.setFile(new File("client_" + args[3] + ".log"));
		
		original(args);
	}

}
