package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.SubcellularLocation;
import org.uniprot.core.uniprot.comment.SubcellularLocationValue;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;

public class SubcellularLocationImpl implements SubcellularLocation {
    private static final long serialVersionUID = -1217106303846658373L;
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

		private static final long serialVersionUID = -2637543322400653482L;
		private String id;
    	private SubcellularLocationValueImpl() {
            super( null, Collections.emptyList());
        }

        public SubcellularLocationValueImpl(String id, String value, List<Evidence> evidences) {
            super(value, evidences);
            this.id =id;
        }

		@Override
		public String getId() {
			return id;
		}
		
    }
}
