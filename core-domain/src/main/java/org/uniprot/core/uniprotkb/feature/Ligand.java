package org.uniprot.core.uniprotkb.feature;

import java.io.Serializable;
/**
 * 
 */
public interface Ligand extends Serializable {
	String getName();

	String getId();

	String getLabel();

	String getNote();
}
