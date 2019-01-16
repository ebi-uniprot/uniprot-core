package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationImpl;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class SubcellularLocationBuilder implements Builder2<SubcellularLocationBuilder, SubcellularLocation> {
    private SubcellularLocationValue location;
    private SubcellularLocationValue topology;
    private SubcellularLocationValue orientation;

    public SubcellularLocationBuilder location(SubcellularLocationValue location) {
        this.location = location;
        return this;
    }

    public SubcellularLocationBuilder topology(SubcellularLocationValue topology) {
        this.topology = topology;
        return this;
    }

    public SubcellularLocationBuilder orientation(SubcellularLocationValue orientation) {
        this.orientation = orientation;
        return this;
    }

    public SubcellularLocationImpl build() {
        return new SubcellularLocationImpl(this);
    }

    @Override
    public SubcellularLocationBuilder from(SubcellularLocation instance) {
        return new SubcellularLocationBuilder()
                .location(instance.getLocation())
                .orientation(instance.getOrientation())
                .topology(instance.getTopology());
    }

    public SubcellularLocationValue getLocation() {
        return location;
    }

    public SubcellularLocationValue getTopology() {
        return topology;
    }

    public SubcellularLocationValue getOrientation() {
        return orientation;
    }
}
