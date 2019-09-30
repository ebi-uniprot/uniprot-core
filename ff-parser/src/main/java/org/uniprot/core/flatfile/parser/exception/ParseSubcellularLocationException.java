package org.uniprot.core.flatfile.parser.exception;

public class ParseSubcellularLocationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ParseSubcellularLocationException() {
        super();
    }

    public ParseSubcellularLocationException(String message) {
        super(message);
    }

    public ParseSubcellularLocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseSubcellularLocationException(Throwable cause) {
        super(cause);
    }
}
