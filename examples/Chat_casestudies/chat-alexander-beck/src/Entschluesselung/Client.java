/**
 * simple chat client
 */
public class Client implements Runnable {

	public void send(String line) {
		original(Verschlüsselung(line));
	}

	private String Verschlüsselung(String text){
		String verschlüsselterText="";
		//--------1. Verschlüsselung-----------
		for(int i=text.length()-1;i>=0;i--){
			verschlüsselterText += text.charAt(i);
		}
		//--------1. Verschlüsselung Ende------

		//--------2. Verschlüsselung-----------
		String[] verschlüsselterTextTemp = verschlüsselterText.split(" ");
		verschlüsselterText="";
		for(int i=verschlüsselterTextTemp.length-1;i>=0;i--){
			verschlüsselterText += verschlüsselterTextTemp[i]+(i>0?" ":"");
			
		}
		//--------2. Verschlüsselung Ende------

		return verschlüsselterText;
	}

}
