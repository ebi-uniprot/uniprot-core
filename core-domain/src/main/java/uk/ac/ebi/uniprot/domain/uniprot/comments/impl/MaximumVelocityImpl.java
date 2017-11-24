package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.Collections;
import java.util.List;

public class MaximumVelocityImpl implements MaximumVelocity {
    private final float velocity;
    private final String unit;
    private final String enzyme;
    private final List<Evidence> evidences;

    public MaximumVelocityImpl(float velocity, String unit, String enzyme, List<Evidence> evidences) {
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
    public float getVelocity() {
        return velocity;
    }

    @Override
    public String getEnzyme() {
        return enzyme;
    }

    @Override
    public String getVelocityUnit() {
        return unit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enzyme == null) ? 0 : enzyme.hashCode());
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((unit == null) ? 0 : unit.hashCode());
        result = prime * result + Float.floatToIntBits(velocity);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MaximumVelocityImpl other = (MaximumVelocityImpl) obj;
        if (enzyme == null) {
            if (other.enzyme != null)
                return false;
        } else if (!enzyme.equals(other.enzyme))
            return false;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (unit == null) {
            if (other.unit != null)
                return false;
        } else if (!unit.equals(other.unit))
            return false;
        if (Float.floatToIntBits(velocity) != Float.floatToIntBits(other.velocity))
            return false;
        return true;
    }

}
