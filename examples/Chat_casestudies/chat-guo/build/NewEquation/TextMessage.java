
package NewEquation;

import java.io.Serializable;

import java.awt.Color;

import java.io.FileWriter;
import java.io.IOException;




abstract class TextMessage$$Chatbasic implements Serializable {

	private static final long serialVersionUID = -9161595018411902079L;
	public String content;
	public String msgHead; /**
	 	                     * msgHead fuer Ipadress oder Name.
	 	                     * weiter fuer andere funktion.
	 	                     * 
	 	                     */
	
	public TextMessage$$Chatbasic() {
		
	}
	
	public TextMessage$$Chatbasic(String content) {
		super();
		this.content = content;
		this.msgHead="------------------";
	}
	
	public TextMessage$$Chatbasic(String msghead, TextMessage msg) {
		super();
		int len = msghead.length();
		if (len < 18) {
			for (int i = 1; i <= 18-len; i++) {
				msghead += "-";
			}

		}

		this.msgHead = msghead;
		this.content = msg.getContent();
	}

	public String getContent() {
		return content;
	}
	
	public String getHead() {
		return msgHead;

	}
	
	public void log(String fName) {
		
	}
}



abstract class TextMessage$$Farbe extends  TextMessage$$Chatbasic  {

	public Color farbe;
	
	public TextMessage$$Farbe(String content) { super(content); 

		((TextMessage) this).farbe=new Color(0, 0, 0); }


	TextMessage$$Farbe(String content, Color farbe) {
		super(content);
		this.farbe=farbe;
		
	}
	
	public TextMessage$$Farbe(String msghead, TextMessage msg) { super(msghead, msg); 

		((TextMessage) this).farbe = msg.getFarbe(); }

	
	public Color getFarbe() {
		return farbe;

	}
      // inherited constructors

 /**
	 	                     * msgHead fuer Ipadress oder Name.
	 	                     * weiter fuer andere funktion.
	 	                     * 
	 	                     */
	
	public TextMessage$$Farbe (  ) { super(); }
	
	
}



abstract class TextMessage$$Historie extends  TextMessage$$Farbe  {


	
	public void log(String fName) {
		super.log(fName);
		FileWriter fw = null;
		try {
			fw = new FileWriter(fName, true);
			fw.write(((TextMessage) this).getHead()+((TextMessage) this).getContent() + "\n");
			fw.close();
		} catch (IOException e) {

			e.printStackTrace();
			System.out.println("Writer error");
			System.exit(-1);
		}
	}
      // inherited constructors


	
	public TextMessage$$Historie ( String content ) { super(content); }

	TextMessage$$Historie ( String content, Color farbe ) { super(content, farbe); }
	
	public TextMessage$$Historie ( String msghead, TextMessage msg ) { super(msghead, msg); } /**
	 	                     * msgHead fuer Ipadress oder Name.
	 	                     * weiter fuer andere funktion.
	 	                     * 
	 	                     */
	
	public TextMessage$$Historie (  ) { super(); }
}



public class TextMessage extends  TextMessage$$Historie  {
	
	public void verschieben(){
	
		((TextMessage) this).content=verschieben(((TextMessage) this).getContent());
	
	}
	
	public void entverschieben(){
	
		((TextMessage) this).content=entverschieben(((TextMessage) this).getContent());
	
	}
	
	public String verschieben(String pf) {

		StringBuffer pf2 = new StringBuffer();

		for (int i = 0; i < pf.length(); i++) {
			int c = pf.charAt(i);
			if (c < 65 || c > 122) {
				pf2.append((char) c);
			} else
				pf2.append((char) (c + 1));

		}
		return pf2.toString();
	}

	public String entverschieben(String pf) {

		StringBuffer pf2 = new StringBuffer();

		for (int i = 0; i < pf.length(); i++) {
			int c = pf.charAt(i);
			if (c < 66 || c > 123) {
				pf2.append((char) c);
			} else
				pf2.append((char) (c - 1));

		}
		return pf2.toString();
	}
      // inherited constructors


	
	public TextMessage ( String content ) { super(content); }

	TextMessage ( String content, Color farbe ) { super(content, farbe); }
	
	public TextMessage ( String msghead, TextMessage msg ) { super(msghead, msg); } /**
	 	                     * msgHead fuer Ipadress oder Name.
	 	                     * weiter fuer andere funktion.
	 	                     * 
	 	                     */
	
	public TextMessage (  ) { super(); }


}