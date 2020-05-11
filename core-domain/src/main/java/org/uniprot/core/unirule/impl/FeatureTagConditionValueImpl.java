package org.uniprot.core.unirule.impl;

import java.util.Objects;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.unirule.FeatureTagConditionValue;

public class FeatureTagConditionValueImpl extends ValueImpl implements FeatureTagConditionValue {

    private String pattern;

    FeatureTagConditionValueImpl() {
        super(null);
    }

    FeatureTagConditionValueImpl(String value, String pattern) {
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
        if (!(o instanceof FeatureTagConditionValueImpl)) return false;
        if (!super.equals(o)) return false;
        FeatureTagConditionValueImpl that = (FeatureTagConditionValueImpl) o;
        return Objects.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pattern);
    }
}
