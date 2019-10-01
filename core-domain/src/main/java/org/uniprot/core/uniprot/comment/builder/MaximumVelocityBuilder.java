package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.comment.MaximumVelocity;
import org.uniprot.core.uniprot.comment.impl.MaximumVelocityImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class MaximumVelocityBuilder implements Builder<MaximumVelocityBuilder, MaximumVelocity> {
    private double velocity;
    private String unit;
    private String enzyme;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public MaximumVelocity build() {
        return new MaximumVelocityImpl(velocity, unit, enzyme, evidences);
    }

    @Override
    public MaximumVelocityBuilder from(MaximumVelocity instance) {
        evidences.clear();
        return this.evidences(instance.getEvidences())
                .enzyme(instance.getEnzyme())
                .unit(instance.getUnit())
                .velocity(instance.getVelocity());
    }

    public MaximumVelocityBuilder velocity(double velocity) {
        this.velocity = velocity;
        return this;
    }

    public MaximumVelocityBuilder unit(String unit) {
        this.unit = unit;
        return this;
    }

    public MaximumVelocityBuilder enzyme(String enzyme) {
        this.enzyme = enzyme;
        return this;
    }

    public MaximumVelocityBuilder evidences(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public MaximumVelocityBuilder addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }
}
