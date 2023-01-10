package exceptions;

/**
 *
 * @author Nando
 */
public class DepartmentNotFoundException extends Exception {

    public DepartmentNotFoundException() {
    }

    public DepartmentNotFoundException(String msg) {
        super(msg);
    }
}
