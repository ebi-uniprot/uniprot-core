package org.uniprot.core.uniref;

import java.io.Serializable;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public interface OverlapRegion extends Serializable {
	int getStart();
	int getEnd();
	String getName();
}

