package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SubcellularLocationImpl implements SubcellularLocation {
    public static SubcellularLocationValue createSubcellularLocationValue(String value, List<Evidence> evidences){
        return new SubcellularLocationValueImpl(value, evidences);
    }
    private final SubcellularLocationValue location;
    private final SubcellularLocationValue topology;
    private final SubcellularLocationValue orientation;
    @JsonCreator
    public SubcellularLocationImpl(
    		@JsonProperty("location") SubcellularLocationValue location,
    		@JsonProperty("topology") SubcellularLocationValue topology,
    		@JsonProperty("orientation") SubcellularLocationValue orientation){
        this.location = location;
        this.topology =topology;
        this.orientation = orientation;
    }
    @Override
    public SubcellularLocationValue getLocation() {
       return location;
    }

    @Override
    public SubcellularLocationValue getTopology() {
        return topology;
    }

    @Override
    public SubcellularLocationValue getOrientation() {
        return orientation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
        result = prime * result + ((topology == null) ? 0 : topology.hashCode());
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
        SubcellularLocationImpl other = (SubcellularLocationImpl) obj;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (orientation == null) {
            if (other.orientation != null)
                return false;
        } else if (!orientation.equals(other.orientation))
            return false;
        if (topology == null) {
            if (other.topology != null)
                return false;
        } else if (!topology.equals(other.topology))
            return false;
        return true;
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class SubcellularLocationValueImpl extends EvidencedValueImpl implements SubcellularLocationValue{
    	 @JsonCreator
        public SubcellularLocationValueImpl(
        		@JsonProperty("value") String value, 
        		@JsonProperty("evidences")List<Evidence> evidences) {
            super(value, evidences);
        }

      
    }
}
