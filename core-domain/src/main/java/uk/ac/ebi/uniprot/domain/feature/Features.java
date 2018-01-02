package uk.ac.ebi.uniprot.domain.feature;

import java.util.List;

public interface Features {
    List<Feature> getAllFeatures();
    <T extends Feature> List<T> getFeatures(FeatureType type);
}
