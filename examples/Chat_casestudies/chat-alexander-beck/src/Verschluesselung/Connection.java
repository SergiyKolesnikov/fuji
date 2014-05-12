public class Connection extends Thread {
	
	
	
	private void handleIncomingMessage(String name, Object msg) {
		 if(msg instanceof TextMessage){
			 String vers = ((TextMessage) msg).getContent();
			 System.out.println("Enc:"+vers);
			 String text = Entschlüsselung(vers);
			 System.out.println("Dec:"+text);
			 original(name, new TextMessage(text)); 
		 }
	}
	
	
	private String Entschlüsselung(String text){
		String entschlüsselterText="";

		//Bei der Art der Verschlüsselung spielt die Reihenfolge der Auflösung keine Rolle. 
		//Korrekt wäre aber eigentlich erst 2. dann 1.
		
		//--------1. Entschlüsselung-----------
		for(int i=text.length()-1;i>=0;i--){
			entschlüsselterText += text.charAt(i);
		}
		//--------1. Entschlüsselung Ende------
		
		//--------2. Entschlüsselung-----------
		String[] entschlüsselterTextTemp = entschlüsselterText.split(" ");
		entschlüsselterText="";
		for(int i=entschlüsselterTextTemp.length-1;i>=0;i--){
			entschlüsselterText += entschlüsselterTextTemp[i]+(i>0?" ":"");
			
		}
		//--------2. Entschlüsselung Ende------
		return entschlüsselterText;
	}
}
