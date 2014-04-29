public class EncryptionPlugin{
	
	public static enum Encoding{
		None,
		Rot13,
		FirstLetterExchange
	}

	public Encoding chosenEncoding;
	
	public EncryptionPlugin(Encoding enc){
		chosenEncoding = enc;
	}
	
	public String handleOutputMessage(String line) {
		StringBuffer encryptedLine = new StringBuffer(line);
		
		if (chosenEncoding == Encoding.Rot13){
			String encrypted = "";
			for(int i=0; i<line.length(); i++){
				char temp = line.charAt(i);
				temp += 13;
				encrypted += temp;
			}
			return encrypted;
		}
		if (chosenEncoding == Encoding.FirstLetterExchange){

			String[]originalWords = line.split("\\s+");
			for(int i =0; i< originalWords.length; i++){
				if (originalWords[i].length() > 2){
					// vertauschen der wšrter
					char firstLetter = originalWords[i].charAt(0);
					char secoundLetter = originalWords[i].charAt(1);
					String encryptedWord = "";
					encryptedWord += secoundLetter; encryptedWord += firstLetter; encryptedWord +=originalWords[i].substring(2); 
					
					// ersetzen des wortes
					int indexWord = line.indexOf(originalWords[i]);					
					encryptedLine.replace(indexWord, indexWord + originalWords[i].length(), encryptedWord);
				}				
			}
		}
		return encryptedLine.toString();
	}

	public String handleInputMessage(String line) {
		
		if (line.indexOf("-") != -1){
			String header = line.substring(0,line.indexOf("-")+1);
			line = line.substring(line.indexOf("-"), line.length());
			
			System.out.println("Encrypted String: " + line);
			StringBuffer encryptedLine = new StringBuffer(line);

			if (chosenEncoding == Encoding.Rot13){
				String decrypted = "";
				for(int i=0; i<line.length(); i++){
					char temp = line.charAt(i);
					if (temp != '\n'){
						temp -= 13;
					}
					decrypted += temp;
				}
				return header + decrypted;
			}
			if (chosenEncoding == Encoding.FirstLetterExchange){

				String[]originalWords = line.split("\\s+");
				for(int i =0; i< originalWords.length; i++){
					if (originalWords[i].length() > 2){
						char firstLetter = originalWords[i].charAt(0);
						char secoundLetter = originalWords[i].charAt(1);
						String decryptedWord = "";
						decryptedWord += secoundLetter; decryptedWord += firstLetter; decryptedWord +=originalWords[i].substring(2); 
						
						// ersetzen des wortes
						int indexWord = line.indexOf(originalWords[i]);					
						encryptedLine.replace(indexWord, indexWord + originalWords[i].length(), decryptedWord);
					}				
				}
			}
			return header + encryptedLine.toString();
		}
		return line;
	}

}
