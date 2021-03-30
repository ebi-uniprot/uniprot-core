package org.uniprot.cv;

/**
 * @author lgonzales
 * @since 30/03/2021
 */
public class FileParseException extends RuntimeException {

    private static final long serialVersionUID = -557508108080374204L;

    public FileParseException(String message) {
        super(message);
    }

    public FileParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
