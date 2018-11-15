package uk.ac.ebi.uniprot.domain.feature.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureDescription;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;

public abstract class FeatureImpl implements Feature {
    private final FeatureType type;
    private final FeatureLocation location;       
   private final FeatureDescription description;

   public static FeatureDescription createDescription(String description ){
       return new FeatureDescriptionImpl(description);
   }

    public FeatureImpl(FeatureType type, FeatureLocation location,        
            String description){
        this.type = type;
        this.location = location;
        this.description = createDescription(description);
    }
 
    @Override
    public FeatureType getType() {
        return type;
    }

    @Override
    public FeatureLocation getFeatureLocation() {
        return location;
    }

    @Override
    public FeatureDescription getDescription() {
       return description;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        FeatureImpl other = (FeatureImpl) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (type != other.type)
            return false;
        return true;
    }
    
    static class FeatureDescriptionImpl implements FeatureDescription {
        private final String description;
        
        public FeatureDescriptionImpl(String description){
            if((description ==null) || description.isEmpty()){
                this.description = "";
            }else{
                
                this.description = description;
            }
        }

        @Override
        public String getValue() {
           return description;
        }

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((description == null) ? 0 : description.hashCode());
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
			FeatureDescriptionImpl other = (FeatureDescriptionImpl) obj;
			if (description == null) {
				if (other.description != null)
					return false;
			} else if (!description.equals(other.description))
				return false;
			return true;
		}

     
    }
}
