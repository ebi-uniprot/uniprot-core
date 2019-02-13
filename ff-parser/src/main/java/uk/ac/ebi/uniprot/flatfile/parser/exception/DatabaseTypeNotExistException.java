package uk.ac.ebi.uniprot.flatfile.parser.exception;

public class DatabaseTypeNotExistException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseTypeNotExistException(String dbType){
		super("DatabaseType: "+dbType +" is not supported");
	}

}
