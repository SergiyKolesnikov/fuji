package fuji;
/**
 * Signals that parser found syntactic errors in the source files.
 * 
 * @author kolesnik
 */

public class SyntacticErrorException extends Exception {
    
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param   message   the detail message.
     */
    public SyntacticErrorException(String message) {
        super(message);
    }
    
    /**
     * Default constructor.
     */
    public SyntacticErrorException() {
        super();
    }
    
    private static final long serialVersionUID = 3239239893656183536L;
}