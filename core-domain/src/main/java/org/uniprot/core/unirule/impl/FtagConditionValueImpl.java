package org.uniprot.core.unirule.impl;

import java.util.Objects;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.unirule.FtagConditionValue;

public class FtagConditionValueImpl extends ValueImpl implements FtagConditionValue {

    private String pattern;

    FtagConditionValueImpl() {
        super(null);
    }

    FtagConditionValueImpl(String value, String pattern) {
        super(value);
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FtagConditionValueImpl)) return false;
        if (!super.equals(o)) return false;
        FtagConditionValueImpl that = (FtagConditionValueImpl) o;
        return Objects.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pattern);
    }
}
