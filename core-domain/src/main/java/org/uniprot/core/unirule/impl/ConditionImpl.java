package org.uniprot.core.unirule.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.Range;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionValue;
import org.uniprot.core.util.Utils;

public class ConditionImpl implements Condition {

    private static final long serialVersionUID = -8009974474671620308L;
    private List<ConditionValue> conditionValues;
    private String type;
    private boolean isNegative;
    private Range range;

    public ConditionImpl(
            List<ConditionValue> conditionValues, String type, boolean isNegative, Range range) {
        this.conditionValues = Utils.unmodifiableList(conditionValues);
        this.type = type;
        this.isNegative = isNegative;
        this.range = range;
    }

    @Override
    public List<ConditionValue> getConditionValues() {
        return conditionValues;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean isNegative() {
        return isNegative;
    }

    @Override
    public Range getRange() {
        return range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionImpl condition = (ConditionImpl) o;
        return isNegative == condition.isNegative
                && Objects.equals(conditionValues, condition.conditionValues)
                && Objects.equals(type, condition.type)
                && Objects.equals(range, condition.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionValues, type, isNegative, range);
    }
}
