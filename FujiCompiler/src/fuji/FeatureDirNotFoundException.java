/**
 * 
 */
package fuji;

/**
 * Signals that one or more feature directories specified in a .features file do
 * not exist or that the .features file is empty.
 * 
 * @author kolesnik
 */
public class FeatureDirNotFoundException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param   message   the detail message.
     */
    public FeatureDirNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Default constructor.
     */
    public FeatureDirNotFoundException() {
        super();
    }

    private static final long serialVersionUID = 5233966237275453376L;    
}
