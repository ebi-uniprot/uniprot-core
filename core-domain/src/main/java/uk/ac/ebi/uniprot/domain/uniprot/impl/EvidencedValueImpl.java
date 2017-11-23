package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EvidencedValueImpl implements EvidencedValue {
    private String value;
    private List<Evidence> evidences;

    public EvidencedValueImpl(String value, List<Evidence> evidences) {
        if(value ==null){
            this.value ="";
        }else
            this.value = value;
        if((evidences !=null) && !evidences.isEmpty())
            this.evidences = Collections.unmodifiableList(evidences);
        else
            this.evidences = Collections.emptyList();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        EvidencedValueImpl other = (EvidencedValueImpl) obj;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return getDisplayedValue("");
    }

    @Override
    public String getDisplayedValue(String separator) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        if (!evidences.isEmpty()) {
            sb.append(separator)
                    .append(" {")
                    .append(
                            evidences.stream()
                                    .map(val -> val.getValue())
                                    .collect(Collectors.joining(", ")))
                    .append("}");
        }
        return sb.toString();
    }

}
