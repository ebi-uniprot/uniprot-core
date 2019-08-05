package org.uniprot.core.flatfile.parser.exception;

public class ParseKeywordException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParseKeywordException() {
		super();
	}
	
	public ParseKeywordException(String message) {
		super(message);
	}
	
	public ParseKeywordException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ParseKeywordException( Throwable cause) {
		super( cause);
	}
}
