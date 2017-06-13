package telibros.exceptions;

/**
 *
 * @author igor
 */
public class GeneroNotFoundException extends Exception {

    public GeneroNotFoundException() {
    }

    /**
     * Constructs an instance of <code>GeneroNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public GeneroNotFoundException(String msg) {
        super(msg);
    }
}
