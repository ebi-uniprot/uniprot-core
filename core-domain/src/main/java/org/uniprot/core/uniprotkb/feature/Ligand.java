package org.uniprot.core.uniprotkb.feature;

import java.io.Serializable;
import java.util.Optional;
/**
 * 
 */
public interface Ligand extends Serializable {
	String getName();

	String getId();

	String getLabel();

	String getNote();

	Optional<LigandPart> getLigandPart();
}
