package uk.ac.ebi.uniprot.domain.exception;


/**
 * FieldUnavailableException
 *
 *
 *
 *
 */
public class FieldUnavailableException extends RuntimeException {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FieldUnavailableException() {
		super();
	}

	public FieldUnavailableException(String message) {
		super(message);
	}
}
