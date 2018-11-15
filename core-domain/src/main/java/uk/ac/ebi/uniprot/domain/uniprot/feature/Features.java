package uk.ac.ebi.uniprot.domain.uniprot.feature;

import java.util.List;


public interface Features {
    List<Feature> getFeatures();
    List<Feature> getFeaturesByType(FeatureType type);
}
