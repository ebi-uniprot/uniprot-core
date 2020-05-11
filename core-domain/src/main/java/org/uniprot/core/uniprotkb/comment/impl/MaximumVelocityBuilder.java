package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.comment.MaximumVelocity;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class MaximumVelocityBuilder implements Builder<MaximumVelocity> {
    private double velocity;
    private String unit;
    private String enzyme;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public @Nonnull MaximumVelocity build() {
        return new MaximumVelocityImpl(velocity, unit, enzyme, evidences);
    }

    public static @Nonnull MaximumVelocityBuilder from(@Nonnull MaximumVelocity instance) {
        return new MaximumVelocityBuilder()
                .evidencesSet(instance.getEvidences())
                .enzyme(instance.getEnzyme())
                .unit(instance.getUnit())
                .velocity(instance.getVelocity());
    }

    public @Nonnull MaximumVelocityBuilder velocity(double velocity) {
        this.velocity = velocity;
        return this;
    }

    public @Nonnull MaximumVelocityBuilder unit(String unit) {
        this.unit = unit;
        return this;
    }

    public @Nonnull MaximumVelocityBuilder enzyme(String enzyme) {
        this.enzyme = enzyme;
        return this;
    }

    public @Nonnull MaximumVelocityBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull MaximumVelocityBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}
