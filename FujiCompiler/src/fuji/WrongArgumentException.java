/**
 * 
 */
package fuji;

/**
 * Signals that one or more command line arguments are wrong.
 * 
 * @author kolesnik
 */
public class WrongArgumentException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param   message   the detail message.
     */
    public WrongArgumentException(String message) {
        super(message);
    }
    
    /**
     * Default constructor.
     */
    public WrongArgumentException() {
        super();
    }
    
    private static final long serialVersionUID = 1L;
}
