package common;

import java.io.Serializable;
import crypt.Crypt;






/**
 * Nachricht, die einen User informiert, ob seine Logindaten
 * bestaetigt wurden.
 * @author Ralf Beczkowiak
 *
 */
abstract class UserConfirmedMessage$$Login$common implements Serializable, IMessage {

    protected static final long serialVersionUID = 2101902412878462084L;
    
    protected boolean confirmed;
    
    /**
     * Erzeugt eine neue Bestaetigungsnachricht, die
     * den User informiert, ob sein Login akzeptiert wurde.
     * @param confirmed Status
     */
    public UserConfirmedMessage$$Login$common(boolean confirmed) {
        this.confirmed = confirmed;
    }
    
    /**
     * Feedback, ob der User zugelassen wurde.
     * @return Feedback
     */
    public boolean isConfirmed() {
        return ((UserConfirmedMessage) this).confirmed;
    }
}



public class UserConfirmedMessage extends  UserConfirmedMessage$$Login$common  {
	protected byte[] confirmedCrypted;
	
	public void encode(Crypt crypt) {
        byte[] array = crypt.encode(String.valueOf(((UserConfirmedMessage) this).confirmed));
        if (array != null) {
            ((UserConfirmedMessage) this).confirmed = false;
            ((UserConfirmedMessage) this).confirmedCrypted = array;
        }
    }

    public void decode(Crypt crypt) {
        Object obj = crypt.decode(((UserConfirmedMessage) this).confirmedCrypted);
        if (obj != null) {
            if (obj instanceof String){
                ((UserConfirmedMessage) this).confirmed = Boolean.parseBoolean((String)obj);
            } else {
                throw new RuntimeException("Failed to decode an login request.");
            }
        }
    }
      // inherited constructors


    
    /**
     * Erzeugt eine neue Bestaetigungsnachricht, die
     * den User informiert, ob sein Login akzeptiert wurde.
     * @param confirmed Status
     */
    public UserConfirmedMessage ( boolean confirmed ) { super(confirmed); }
}