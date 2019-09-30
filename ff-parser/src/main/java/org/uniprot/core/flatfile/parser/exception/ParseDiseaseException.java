package org.uniprot.core.flatfile.parser.exception;

public class ParseDiseaseException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 1L;

    public ParseDiseaseException() {
        super();
    }

    public ParseDiseaseException(String message) {
        super(message);
    }

    public ParseDiseaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseDiseaseException(Throwable cause) {
        super(cause);
    }
}
