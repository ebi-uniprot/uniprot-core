package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MaximumVelocityImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class MaximumVelocityBuilder implements Builder2<MaximumVelocityBuilder, MaximumVelocity> {
    private double velocity;
    private String unit;
    private String enzyme;
    private List<Evidence> evidences = new ArrayList<>();

    @Override
    public MaximumVelocity build() {
        return new MaximumVelocityImpl(this);
    }

    @Override
    public MaximumVelocityBuilder from(MaximumVelocity instance) {
        return new MaximumVelocityBuilder()
                .evidences(instance.getEvidences())
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
        this.evidences.addAll(evidences);
        return this;
    }

    public MaximumVelocityBuilder addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }

    public double getVelocity() {
        return velocity;
    }

    public String getUnit() {
        return unit;
    }

    public String getEnzyme() {
        return enzyme;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }
}
