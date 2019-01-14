package de.uniba.dsg.dsam.customexceptions;

/**
 * User: sini_ann
 * Date: 10/12/18 8:42 PM
 */
public class BeverageException  extends Exception  {

    public BeverageException() {
    }

    public BeverageException(String message) {
        super(message);
    }

    public BeverageException(Throwable cause) {
        super(cause);
    }

    public BeverageException(String message, Throwable cause) {
        super(message, cause);
    }
}
