package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MaximumVelocityImpl implements MaximumVelocity, Serializable {
    private static final long serialVersionUID = -1145888231924505152L;
    private double velocity;
    private String unit;
    private String enzyme;
    private List<Evidence> evidences;

    private MaximumVelocityImpl() {
        this.evidences = Collections.emptyList();
    }

    public MaximumVelocityImpl(double velocity,
                               String unit,
                               String enzyme,
                               List<Evidence> evidences) {
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
