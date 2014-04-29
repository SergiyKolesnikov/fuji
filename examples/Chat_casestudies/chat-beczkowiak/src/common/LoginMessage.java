package common;

import java.io.Serializable;
import crypt.Crypt;



/**
 * Serialisierbare Klasse zum Versenden der Login-Daten.
 * @author Ralf Beczkowiak
 *
 */
abstract class LoginMessage$$Login$common implements Serializable, IMessage {

    protected static final long serialVersionUID = 4047863306110543911L;
    
    protected String name;
    protected char[] password;
    
    /**
     * Erzeugt eine neue Loginmessage zum Versenden.
     * @param name Benutzername
     * @param password Passwort
     */
    public LoginMessage$$Login$common(String name, char[] password) {
        this.name = name;
        this.password = password;
    }
    
    /**
     * Gibt den Benutzernamen zurueck.
     * @return Benutzername
     */
    public String getName() {
        return ((LoginMessage) this).name;
    }
    
    /**
     * Gibt das Passwort zurueck.
     * @return Passwort
     */
    public char[] getPassword() {
        return ((LoginMessage) this).password;
    }
    
}



public class LoginMessage extends  LoginMessage$$Login$common  {
	protected byte[] nameCrypted;
	protected byte[] passwordCrypted;
	
	public void encode(Crypt crypt) {
        byte[] array = crypt.encode(((LoginMessage) this).name);
        if (array != null) {
            ((LoginMessage) this).name = "";
            ((LoginMessage) this).nameCrypted = array;
            array = crypt.encode(((LoginMessage) this).password);
            ((LoginMessage) this).password = new char[] {};
            ((LoginMessage) this).passwordCrypted = array;
        }
    }

	public void decode(Crypt crypt) {
        Object obj = crypt.decode(((LoginMessage) this).nameCrypted);
        if (obj != null) {
			if (obj instanceof String){
				((LoginMessage) this).name = (String) obj;
        	} else {
        	    throw new RuntimeException("Failed to decode an login request.");
			}
			obj = crypt.decode(((LoginMessage) this).passwordCrypted);
			if (obj instanceof char[]){
      	          ((LoginMessage) this).password = (char[]) obj;
			} else {
				throw new RuntimeException("Failed to decode an login request.");
			}
        }
	}
      // inherited constructors


    
    /**
     * Erzeugt eine neue Loginmessage zum Versenden.
     * @param name Benutzername
     * @param password Passwort
     */
    public LoginMessage ( String name, char[] password ) { super(name, password); }
}