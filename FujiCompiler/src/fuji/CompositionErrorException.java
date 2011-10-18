package fuji;
/**
 * Signals errors during feature composition .
 * 
 * @author kolesnik
 */

public class CompositionErrorException extends RuntimeException {
    
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param   message   the detail message.
     */
    public CompositionErrorException(String message) {
        super(message);
    }
    
    /**
     * Default constructor.
     */
    public CompositionErrorException() {
        super();
    }

    private static final long serialVersionUID = 5924664109303700496L;
}