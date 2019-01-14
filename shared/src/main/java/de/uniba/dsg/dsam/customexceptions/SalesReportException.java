package de.uniba.dsg.dsam.customexceptions;

/**
 * User: sini_ann
 * Date: 10/12/18 8:43 PM
 */
public class SalesReportException extends Exception {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public SalesReportException() {
    }

    public SalesReportException(String message) {
        super(message);
    }

    public SalesReportException(Throwable cause) {
        super(cause);
    }

    public SalesReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
