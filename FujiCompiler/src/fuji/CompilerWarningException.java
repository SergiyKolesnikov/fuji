package fuji;
/**
 * Signals that compiler generated some warnings for the program.
 * 
 * @author kolesnik
 */

public class CompilerWarningException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param   message   the detail message.
     */
    public CompilerWarningException(String message) {
        super(message);
    }
    
    /**
     * Default constructor.
     */
    public CompilerWarningException() {
        super();
    }
    
    private static final long serialVersionUID = -2475663063460098917L;
}