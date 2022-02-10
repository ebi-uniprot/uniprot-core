package org.uniprot.core.uniprotkb.feature;

import java.io.Serializable;

/**
 *
 * @author jluo
 * @date: 9 Feb 2022
 *
*/

public interface LigandPart  extends Serializable {
	String getName();
	String getId();
	String getLabel();
	String getNote();
}

