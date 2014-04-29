

/**
 * TODO description
 */
public class Rot13 implements Codierer{
    private String alphabet = "abcdefghijklmnopqrstuvwxyz"; 
    
    public Rot13(){
        
    }
    
    public String verschluesseln(String message) {
        String ciffrat = "";
        for (int i = 0; i < message.length(); i++)
        {
            int pos = alphabet.indexOf(message.charAt(i));
            if (pos != -1)
            {
                ciffrat += alphabet.charAt((pos + 13)%26);
            }
            else
            {
                ciffrat += message.charAt(i);
            }
        }
        return ciffrat;
    }

    public String entschluesseln(String message) {
        String ciffrat = "";
        for (int i = 0; i < message.length(); i++)
        {
            int pos = alphabet.indexOf(message.charAt(i));
            if (pos != -1)
            {
                ciffrat += alphabet.charAt((pos - 13+26)%26);
            }
            else
            {
                ciffrat += message.charAt(i);
            }
        }
        return ciffrat;
    }

    public String gibName() {
        return "ROT_13";
    }
    
}