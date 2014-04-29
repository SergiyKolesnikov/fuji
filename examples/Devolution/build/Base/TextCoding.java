package Base;

public class TextCoding {

	public static String rot48(String text){
		String code = "";

		for(int i=0; i<text.length(); i++){
			int c = text.charAt(i);

			if(c < 33 || c > 126) code = code + (char) c;
			else
				if(c+47<=126)
					code = code + (char) (c+47);
				else
					code = code + (char) (((c+47)%126)+32);
		}

		return code;
	}

}