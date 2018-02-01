package uk.ac.ebi.uniprot.domain.feature.impl;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.HasFeatureId;

import java.util.regex.Pattern;

public class FeatureWithFeatureIdImpl extends FeatureImpl implements Feature, HasFeatureId {
    private final FeatureId featureId;
    private final Pattern featureIdPattern;

    public static FeatureId createFeatureId(String fId) {
        if (fId != null)
            return new FeatureIdImpl(fId);
        else
            return null;
    }
    
    public FeatureWithFeatureIdImpl(FeatureType type, FeatureLocation location, String description,
        String featureId, Pattern featureIdPattern) {
        super(type, location, description);
        this.featureId = createFeatureId(featureId);
        this.featureIdPattern = featureIdPattern;

    }


    @Override
    public FeatureId getFeatureId() {
        return featureId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((featureId == null) ? 0 : featureId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        FeatureWithFeatureIdImpl other = (FeatureWithFeatureIdImpl) obj;
        if (featureId == null) {
            if (other.featureId != null)
                return false;
        } else if (!featureId.equals(other.featureId))
            return false;
        return true;
    }

   static class FeatureIdImpl implements FeatureId {
        private final String value;

        public FeatureIdImpl(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((value == null) ? 0 : value.hashCode());
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
            FeatureIdImpl other = (FeatureIdImpl) obj;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }

    }

	@Override
	public Pattern getFeatureIdPattern() {
		return this.featureIdPattern;
	}
}
