package org.uniprot.core.uniref;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public interface UniRefDatabase extends Serializable {
	UniRefDatabaseType getType();
	String getVersion();
	LocalDate getReleaseDate();
}

