package uk.ac.ebi.uniprot.domain.uniprot;

import java.util.List;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;

public interface UniProtFeatures {
    List<UniProtFeature<? extends Feature>> getFeatues();
    List<UniProtFeature <? extends Feature> > getFeaturesByType(FeatureType type);
}
