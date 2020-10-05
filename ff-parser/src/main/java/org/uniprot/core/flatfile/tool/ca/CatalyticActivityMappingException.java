package org.uniprot.core.flatfile.tool.ca;

public class CatalyticActivityMappingException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3143837440431504307L;
	public CatalyticActivityMappingException(String msg) {
		super(msg);
	}
	public CatalyticActivityMappingException(String msg, Throwable e) {
		super(msg, e );
	}
	
}
