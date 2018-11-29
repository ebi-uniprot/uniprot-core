package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MichaelisConstantImpl implements MichaelisConstant {
    private final double constant;
    private final MichaelisConstantUnit unit;
    private final String substrate;
    private final List<Evidence> evidences;
    @JsonCreator
    public MichaelisConstantImpl(@JsonProperty("constant") double constant, 
    		@JsonProperty("unit") MichaelisConstantUnit unit, 
    		@JsonProperty("substrate")  String substrate,
    		@JsonProperty("evidences")  List<Evidence> evidences) {
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
    public double getConstant() {
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
		long temp;
		temp = Double.doubleToLongBits(constant);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (Double.doubleToLongBits(constant) != Double.doubleToLongBits(other.constant))
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
