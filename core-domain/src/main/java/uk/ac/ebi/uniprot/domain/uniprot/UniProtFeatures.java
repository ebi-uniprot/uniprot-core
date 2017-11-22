package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;

import java.util.List;

public interface UniProtFeatures {
    List<UniProtFeature<? extends Feature>> getFeatues();
    List<UniProtFeature <? extends Feature> > getFeaturesByType(FeatureType type);
}
