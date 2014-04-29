import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging{

	private Logger log;

	public Logging(){
		log = Logger.getLogger(this.getClass().getName()
				);
		log.setLevel(Level.INFO); 
		Handler handler;

		try {
			handler = new FileHandler( "log.txt" );
			log.addHandler( handler );
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public Logger getLogger(){
		return log;
	}
	
}
