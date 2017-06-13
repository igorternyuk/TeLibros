package telibros.exceptions;

/**
 *
 * @author igor
 */
public class AutorNotFoundException extends Exception {

    public AutorNotFoundException() {
    }

    /**
     * Constructs an instance of <code>AutorNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AutorNotFoundException(String msg) {
        super(msg);
    }
}
