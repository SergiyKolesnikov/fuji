package common; 

import java.io.Serializable; 

/**
 * 
 * @author Mikka
 *
 */
public  interface  ITextMessage  extends Serializable {
	
	String getAuthor();

	
	String getContent();

	
	String toString();


}
