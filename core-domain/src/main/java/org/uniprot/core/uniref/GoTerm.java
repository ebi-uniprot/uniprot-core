package org.uniprot.core.uniref;

import java.io.Serializable;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public interface GoTerm extends Serializable{
	GoTermType getType();
	String getId();
}

