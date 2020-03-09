package org.uniprot.core.uniprot.comment.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.comment.SubcellularLocation;
import org.uniprot.core.uniprot.comment.SubcellularLocationValue;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class SubcellularLocationBuilder implements Builder<SubcellularLocation> {
    private SubcellularLocationValue location;
    private SubcellularLocationValue topology;
    private SubcellularLocationValue orientation;

    public @Nonnull SubcellularLocationBuilder location(SubcellularLocationValue location) {
        this.location = location;
        return this;
    }

    public @Nonnull SubcellularLocationBuilder topology(SubcellularLocationValue topology) {
        this.topology = topology;
        return this;
    }

    public @Nonnull SubcellularLocationBuilder orientation(SubcellularLocationValue orientation) {
        this.orientation = orientation;
        return this;
    }

    public @Nonnull SubcellularLocationImpl build() {
        return new SubcellularLocationImpl(location, topology, orientation);
    }

    public static @Nonnull SubcellularLocationBuilder from(@Nonnull SubcellularLocation instance) {
        return new SubcellularLocationBuilder()
                .location(instance.getLocation())
                .orientation(instance.getOrientation())
                .topology(instance.getTopology());
    }
}
