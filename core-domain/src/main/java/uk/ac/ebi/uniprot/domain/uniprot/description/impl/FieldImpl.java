package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.uniprot.description.Field;
import uk.ac.ebi.uniprot.domain.uniprot.description.FieldType;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;
import java.util.List;

public class FieldImpl extends EvidencedValueImpl implements Field {
    private final FieldType type;
    public FieldImpl(FieldType type, String value, List<Evidence> evidences) {
        super(value, evidences);
        this.type = type;
    }

    @Override
    public FieldType getType() {
       return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        FieldImpl other = (FieldImpl) obj;
        if (type != other.type)
            return false;
        return true;
    }

}
