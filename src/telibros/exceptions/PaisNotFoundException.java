package telibros.exceptions;

/**
 *
 * @author igor
 */
public class PaisNotFoundException extends Exception {

    public PaisNotFoundException() {
    }

    /**
     * Constructs an instance of <code>PaisNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PaisNotFoundException(String msg) {
        super(msg);
    }
}
