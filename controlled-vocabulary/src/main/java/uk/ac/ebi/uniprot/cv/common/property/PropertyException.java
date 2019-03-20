package uk.ac.ebi.uniprot.cv.common.property;

/**
 * The JSONException is thrown by the JSON.org classes when things are amiss.
 *
 * @author JSON.org
 * @version 2015-12-09
 */
public class PropertyException extends RuntimeException {
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 0;

    /**
     * Constructs a JSONException with an explanatory message.
     *
     * @param message Detail about the reason for the exception.
     */
    public PropertyException(final String message) {
        super(message);
    }

    /**
     * Constructs a JSONException with an explanatory message and cause.
     *
     * @param message Detail about the reason for the exception.
     * @param cause   The cause.
     */
    public PropertyException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new JSONException with the specified cause.
     *
     * @param cause The cause.
     */
    public PropertyException(final Throwable cause) {
        super(cause.getMessage(), cause);
    }

}
