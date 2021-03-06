package org.uniprot.core.flatfile.parser.exception;

public class DatabaseTypeNotExistException extends RuntimeException {

    /** */
    private static final long serialVersionUID = 1L;

    public DatabaseTypeNotExistException(String dbType) {
        super("Database: " + dbType + " is not supported");
    }
}
