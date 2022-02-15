package org.uniprot.core.uniprotkb.feature;

import org.uniprot.core.feature.Feature;

public interface UniProtKBFeature extends Feature<UniprotKBFeatureDatabase, UniprotKBFeatureType> {

    FeatureId getFeatureId();

    AlternativeSequence getAlternativeSequence();

    Ligand getLigand();

    LigandPart getLigandPart();

    boolean hasFeatureId();

    boolean hasAlternativeSequence();

    boolean hasLigand();

    boolean hasLigandPart();
}
