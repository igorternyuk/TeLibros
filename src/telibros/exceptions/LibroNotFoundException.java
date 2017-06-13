package telibros.exceptions;

/**
 *
 * @author igor
 */
public class LibroNotFoundException extends Exception {

    public LibroNotFoundException() {
    }

    /**
     * Constructs an instance of <code>LibroNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LibroNotFoundException(String msg) {
        super(msg);
    }
}
