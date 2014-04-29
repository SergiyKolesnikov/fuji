
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.Name;

/**
 * TODO description
 */
abstract class Anmelden$$Basis {
	protected String host = "localhost";
	protected int port = 8888;
	
	public Anmelden$$Basis(){}
	
	public String gibHost(){
		return host;
	}
	
	public int gibPort(){
		return port;
	}

}



/**
 * TODO description
 */
public class Anmelden extends  Anmelden$$Basis  {
	
	/**
	 * Fragt die ServerDaten vom Nutzer Ã¯Â¿Â½ber die Komandozeile ab.
	 */
	public void ServerAbfrage(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// Host abfragen
		do{
			try{
				System.out.print("Host (Standard= \"localhost\"): ");
				host = br.readLine();
				if (host.isEmpty())
				{
					host = "localhost";
				}
			}
			catch(Exception e){
				System.out.println("Falsche Eingabe!\n");
				continue;
			}
			break;
		}while(true);
		

		// Port abfragen
		do{
			try{
				System.out.print("Port (Standard= \"8888\"): ");
				String portString = br.readLine();
				if (portString.isEmpty())
				{
					portString = "8888";
				}
				port = Integer.parseInt(portString);
			}
			catch(Exception e){
				System.out.println("Falsche Eingabe!\n");
				continue;
			}
			break;
		}while(true);
		
//		try{
//			br.close();
//		} catch (Exception e){
//			e.printStackTrace();
//		}
	}
      // inherited constructors


	
	public Anmelden (  ) { super(); }
	
//	public void NutzerAbfrage(){
//		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
////		Scanner sc = new Scanner(System.in);
//		
//		// Host abfragen
//		do{
//			try{
//				System.out.print("Name: ");
//				name = bf.readLine();
//			}
//			catch(Exception e){
//				System.out.println("Falsche Eingabe!\n");
//				continue;
//			}
//			break;
//		}while(true);
//		
//
//		// Port abfragen
//		do{
//			try{
//				System.out.print("Passwort: ");
//				passwort = bf.readLine();
//			}
//			catch(Exception e){
//				System.out.println("Falsche Eingabe!\n");
//				continue;
//			}
//			break;
//		}while(true);
//		
//		try{
//			bf.close();
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//	}
	
}