package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationImpl;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class SubcellularLocationBuilder implements Builder<SubcellularLocationBuilder, SubcellularLocation> {
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
        return new SubcellularLocationImpl(location, topology, orientation);
    }

    @Override
    public SubcellularLocationBuilder from(SubcellularLocation instance) {
        return this
                .location(instance.getLocation())
                .orientation(instance.getOrientation())
                .topology(instance.getTopology());
    }
}
