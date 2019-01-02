package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

import java.util.Collections;
import java.util.List;

public class SubcellularLocationImpl implements SubcellularLocation {

    private SubcellularLocationValue location;
    private SubcellularLocationValue topology;
    private SubcellularLocationValue orientation;

    private SubcellularLocationImpl() {

    }

    public SubcellularLocationImpl(
            SubcellularLocationValue location,
            SubcellularLocationValue topology,
            SubcellularLocationValue orientation) {
        this.location = location;
        this.topology = topology;
        this.orientation = orientation;
    }

    public static SubcellularLocationValue createSubcellularLocationValue(String value, List<Evidence> evidences) {
        return new SubcellularLocationValueImpl(value, evidences);
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

    public static class SubcellularLocationValueImpl extends EvidencedValueImpl implements SubcellularLocationValue {

        private SubcellularLocationValueImpl() {
            super(null, Collections.emptyList());
        }

        public SubcellularLocationValueImpl(String value, List<Evidence> evidences) {
            super(value, evidences);
        }


    }
}
