package uk.ac.ebi.uniprot.parser;

public class DatabaseTypeNotExistException extends RuntimeException {
	
	public DatabaseTypeNotExistException(String dbType){
		super("DatabaseType: "+dbType +" is not supported");
	}

}
