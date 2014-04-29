
import java.util.ArrayList;/**
 * TODO description
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.xml.stream.events.StartDocument;



/**
 * TODO description
 */
abstract class EPMD$$Basis {
	
	public static void main (String [] args){
    	/* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
		javax.swing.UIManager.LookAndFeelInfo[] info = javax.swing.UIManager.getInstalledLookAndFeels();
        try {
            for (int i = 0; 0 < info.length; i++) {
                if ("Nimbus".equals(info[i].getName())) {
                    javax.swing.UIManager.setLookAndFeel(info[i].getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

}



public class EPMD extends  EPMD$$Basis  implements Runnable {
	private static DataInputStream din;
	private static DataOutputStream dout;
	
	public static void main(String [] args){
		EPMD$$Basis.main(args);
		start();
	}
	
	public static void start (){
		// Connect to the server
		try {
			Anmelden anmelden = new Anmelden();
			anmelden.ServerAbfrage();
//			anmelden.NutzerAbfrage();
			
			// Initiate the connection
			Socket serversocket = new Socket( anmelden.gibHost(), anmelden.gibPort());

			// We got a connection! Tell the world
			System.out.println( "connected to "+serversocket );

			// Let's grab the streams and create DataInput/Output streams
			// from them
			din = new DataInputStream( serversocket.getInputStream() );
			dout = new DataOutputStream( serversocket.getOutputStream() );
			
			// Start a background thread for receiving messages
			new Thread(new EPMD()).start();
			
		} catch( IOException ie ) { ie.printStackTrace(); }
	}
	
	public void run() {
		try {
			// Receive messages one-by-one, forever
			while (true) {
				// Get the next message
				String message = din.readUTF();
	                
				System.out.print(entferneFarben(message));
			}
		} catch( Exception ie ) { ie.printStackTrace(); }    
	}
	    
    protected String entferneFarben(String nachricht){
		nachricht = nachricht.replace("<blau>", "");
		nachricht = nachricht.replace("</blau>", "");
		nachricht = nachricht.replace("<rot>", "");
		nachricht = nachricht.replace("</rot>", "");
		nachricht = nachricht.replace("<gelb>", "");
		nachricht = nachricht.replace("</gelb>", "");
		nachricht = nachricht.replace("<gruen>", "");
		nachricht = nachricht.replace("</gruen>", "");
		return nachricht;
    }
}