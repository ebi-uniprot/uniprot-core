package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.MaximumVelocity;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class MaximumVelocityImpl implements MaximumVelocity {
    private static final long serialVersionUID = -5733295713214255112L;
    private double velocity;
    private String unit;
    private String enzyme;
    private List<Evidence> evidences;

    private MaximumVelocityImpl() {
        this.evidences = Collections.emptyList();
    }

    public MaximumVelocityImpl(
            double velocity, String unit, String enzyme, List<Evidence> evidences) {
        this.velocity = velocity;
        this.unit = unit;
        this.enzyme = enzyme;
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullOrEmpty(this.evidences);
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
        return Double.compare(that.velocity, velocity) == 0
                && Objects.equals(unit, that.unit)
                && Objects.equals(enzyme, that.enzyme)
                && Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(velocity, unit, enzyme, evidences);
    }
}
