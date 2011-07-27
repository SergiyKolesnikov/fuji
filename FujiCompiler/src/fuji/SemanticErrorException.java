package fuji;
/**
 * Signals that compiler found semantic errors in the program.
 * 
 * @author kolesnik
 */

public class SemanticErrorException extends Exception {
    
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param   message   the detail message.
     */
    public SemanticErrorException(String message) {
        super(message);
    }
    
    /**
     * Default constructor.
     */
    public SemanticErrorException() {
        super();
    }

    private static final long serialVersionUID = 5924664109303700496L;
}