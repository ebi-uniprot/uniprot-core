package uk.ac.ebi.uniprot.domain.uniprot.comments;

/**
 * 
 * @author jieluo
 * @see CofactorComment
 * @version 1.0
 */
public interface CofactorReference {
	/**
	 * 
	 * @return cofactor reference type, ChEBI
	 */
	CofactorReferenceType getCofactorReferenceType();
	/**
	 * 
	 * @return reference id
	 */
	String getReferenceId();
	
}
