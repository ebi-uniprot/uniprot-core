package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.MaximumVelocityBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MaximumVelocityImpl implements MaximumVelocity {
    private double velocity;
    private String unit;
    private String enzyme;
    private List<Evidence> evidences;

    private MaximumVelocityImpl() {
        this.evidences = Collections.emptyList();
    }

    public MaximumVelocityImpl(MaximumVelocityBuilder builder) {
        this.velocity = builder.getVelocity();
        this.unit = builder.getUnit();
        this.enzyme = builder.getEnzyme();
        if ((builder.getEvidences() == null) || builder.getEvidences().isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(builder.getEvidences());
        }
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public double getVelocity() {
        return velocity;
    }

    @Override
    public String getEnzyme() {
        return enzyme;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaximumVelocityImpl that = (MaximumVelocityImpl) o;
        return Double.compare(that.velocity, velocity) == 0 &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(enzyme, that.enzyme) &&
                Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(velocity, unit, enzyme, evidences);
    }
}
