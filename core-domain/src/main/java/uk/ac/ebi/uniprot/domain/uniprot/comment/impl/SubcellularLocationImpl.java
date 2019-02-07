package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidencedValueImpl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SubcellularLocationImpl implements SubcellularLocation {
    private SubcellularLocationValue location;
    private SubcellularLocationValue topology;
    private SubcellularLocationValue orientation;

    private SubcellularLocationImpl() {}

    public SubcellularLocationImpl(
            SubcellularLocationValue location,
            SubcellularLocationValue topology,
            SubcellularLocationValue orientation){
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
    public boolean hasLocation() {
        return this.location != null;
    }

    @Override
    public boolean hasTopology() {
        return this.topology != null;
    }

    @Override
    public boolean hasOrientation() {
        return this.orientation != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcellularLocationImpl that = (SubcellularLocationImpl) o;
        return Objects.equals(location, that.location) &&
                Objects.equals(topology, that.topology) &&
                Objects.equals(orientation, that.orientation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, topology, orientation);
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
