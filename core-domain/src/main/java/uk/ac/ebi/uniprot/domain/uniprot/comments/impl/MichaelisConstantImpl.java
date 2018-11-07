package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;

public class MichaelisConstantImpl implements MichaelisConstant {
    private final float constant;
    private final MichaelisConstantUnit unit;
    private final String substrate;
    private final List<Evidence> evidences;

    public MichaelisConstantImpl(float constant, MichaelisConstantUnit unit, String substrate,
        List<Evidence> evidences) {
        this.constant = constant;
        this.unit = unit;
        this.substrate = substrate;
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
    public float getConstant() {
        return constant;
    }

    @Override
    public MichaelisConstantUnit getUnit() {
        return unit;
    }

    @Override
    public String getSubstrate() {
        return substrate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(constant);
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((substrate == null) ? 0 : substrate.hashCode());
        result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
        MichaelisConstantImpl other = (MichaelisConstantImpl) obj;
        if (Float.floatToIntBits(constant) != Float.floatToIntBits(other.constant))
            return false;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (substrate == null) {
            if (other.substrate != null)
                return false;
        } else if (!substrate.equals(other.substrate))
            return false;
        if (unit != other.unit)
            return false;
        return true;
    }

}
