package uk.ac.ebi.uniprot.domain.feature.impl;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.Features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeaturesImpl implements Features {
    private final List<Feature> features;
    public FeaturesImpl(List<Feature> features){
        if ((features == null) || features.isEmpty()) {
            this.features = Collections.emptyList();
        } else {
            this.features = Collections.unmodifiableList(features);
        }
    }
    
    @Override
    public List<Feature> getAllFeatures() {
        return features;
    }

    @Override
    public <T extends Feature> List<T> getFeatures(FeatureType type) {
        List< Feature> typedFeatures = new ArrayList<>();
        for(Feature feature: features){
            if(feature.getType() == type){
                typedFeatures.add(feature);
            }
        }
        return (List<T>) typedFeatures;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((features == null) ? 0 : features.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FeaturesImpl other = (FeaturesImpl) obj;
        if (features == null) {
            if (other.features != null)
                return false;
        } else if (!features.equals(other.features))
            return false;
        return true;
    }

}
