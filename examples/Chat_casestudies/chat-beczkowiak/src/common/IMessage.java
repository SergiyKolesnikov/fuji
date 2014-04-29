package common;

import crypt.Crypt;



/**
 * Interface um eine Nachrichtenklasse als solche zu Kennzeichnen.
 * @author Ralf Beczkowiak
 *
 */
abstract interface IMessage$$Base$common {
}



public interface IMessage extends IMessage$$Base$common {
	public void encode(Crypt crypt);
    
    public void decode(Crypt crypt);
}