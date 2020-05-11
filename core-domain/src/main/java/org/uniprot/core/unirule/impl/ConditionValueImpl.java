package org.uniprot.core.unirule.impl;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.unirule.ConditionValue;

import java.util.Objects;

public class ConditionValueImpl extends ValueImpl implements ConditionValue {

    private static final long serialVersionUID = -4583284310650267652L;
    private String cvId;

    ConditionValueImpl() {
        super(null);
    }

    ConditionValueImpl(String value, String cvId) {
        super(value);
        this.cvId = cvId;
    }

    @Override
    public String getCvId() {
        return cvId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConditionValueImpl)) return false;
        if (!super.equals(o)) return false;
        ConditionValueImpl that = (ConditionValueImpl) o;
        return Objects.equals(cvId, that.cvId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cvId);
    }
}
