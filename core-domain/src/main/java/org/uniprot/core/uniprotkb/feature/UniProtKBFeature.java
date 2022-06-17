package org.uniprot.core.uniprotkb.feature;

import org.uniprot.core.feature.Feature;
import org.uniprot.core.util.Utils;

public interface UniProtKBFeature extends Feature<UniprotKBFeatureDatabase, UniprotKBFeatureType> {

    FeatureId getFeatureId();

    AlternativeSequence getAlternativeSequence();
    
    Ligand getLigand();
    LigandPart getLigandPart();

    boolean hasFeatureId();

    boolean hasAlternativeSequence();
    
    default boolean hasLigand() {
    	return this.getType() == UniprotKBFeatureType.BINDING;
    }
   default boolean hasLigandPart() {
	   return (this.getType() == UniprotKBFeatureType.BINDING) && Utils.notNull(getLigandPart()); 
   }
}
