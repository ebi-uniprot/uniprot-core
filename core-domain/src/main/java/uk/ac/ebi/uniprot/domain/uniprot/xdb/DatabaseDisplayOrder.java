package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import java.util.List;

import uk.ac.ebi.uniprot.domain.DatabaseType;

/**
 * 
 * @author jieluo
 * @date   22 Nov 2017
 * @time   13:22:56
 *
 */
public interface DatabaseDisplayOrder<T extends DatabaseType> {
	 List<T> getOrderedDatabases(); 
}
