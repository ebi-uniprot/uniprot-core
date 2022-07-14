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
        return Utils.notNull(getLigand());
    }

    default boolean hasLigandPart() {
        return Utils.notNull(getLigandPart());
    }
}
