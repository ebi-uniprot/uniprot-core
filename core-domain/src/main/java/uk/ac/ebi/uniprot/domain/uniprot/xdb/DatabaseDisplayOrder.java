package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import java.util.List;

/**
 * 
 * @author jieluo
 * @date   22 Nov 2017
 * @time   13:22:56
 *
 */
public interface DatabaseDisplayOrder<T> {
	 List<T> getOrderedDatabases(); 
}
