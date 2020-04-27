package org.uniprot.core.unirule.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.Range;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionValue;
import org.uniprot.core.unirule.FtagConditionValue;
import org.uniprot.core.util.Utils;

public class ConditionImpl implements Condition {

    private static final long serialVersionUID = -8009974474671620308L;
    private List<ConditionValue> conditionValues;
    private String type;
    private boolean isNegative;
    private Range range;
    private FtagConditionValue tag;

    ConditionImpl() {
        this.conditionValues = Collections.emptyList();
    }

    ConditionImpl(
            List<ConditionValue> conditionValues,
            String type,
            boolean isNegative,
            Range range,
            FtagConditionValue tag) {
        if (Utils.nullOrEmpty(type)) {
            throw new IllegalArgumentException(
                    "type is a mandatory param for UniRule condition entry.");
        }
        this.conditionValues = Utils.unmodifiableList(conditionValues);
        this.type = type;
        this.isNegative = isNegative;
        this.range = range;
        this.tag = tag;
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
    public FtagConditionValue getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionImpl condition = (ConditionImpl) o;
        return isNegative == condition.isNegative
                && Objects.equals(conditionValues, condition.conditionValues)
                && Objects.equals(type, condition.type)
                && Objects.equals(range, condition.range)
                && Objects.equals(tag, condition.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionValues, type, isNegative, range, tag);
    }
}
