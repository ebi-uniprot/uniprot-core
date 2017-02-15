package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.io.Serializable;
/**
 * 
 * @author jieluo
 * @see CofactorComment
 * @version 1.0
 */
public interface CofactorReference extends Serializable {
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
