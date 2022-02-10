package org.uniprot.core.uniprotkb.feature;

import java.io.Serializable;

/**
 *
 * @author jluo
 * @date: 8 Feb 2022
 *
*/

public interface Ligand extends Serializable {
	String getName();
	String getId();
	String getLabel();
	String getNote();
}

