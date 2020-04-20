package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Range;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionValue;

public class ConditionBuilder implements Builder<Condition> {
    private List<ConditionValue> conditionValues = new ArrayList<>();
    private String type;
    private boolean isNegative;
    private Range range;

    public ConditionBuilder(String type) {
        this.type = type;
    }

    public @Nonnull ConditionBuilder conditionValuesAdd(ConditionValue conditionValue) {
        addOrIgnoreNull(conditionValue, this.conditionValues);
        return this;
    }

    public @Nonnull ConditionBuilder conditionValuesSet(List<ConditionValue> conditionValues) {
        this.conditionValues = modifiableList(conditionValues);
        return this;
    }

    public @Nonnull ConditionBuilder type(String type) {
        this.type = type;
        return this;
    }

    public @Nonnull ConditionBuilder negative(boolean negative) {
        isNegative = negative;
        return this;
    }

    public @Nonnull ConditionBuilder range(Range range) {
        this.range = range;
        return this;
    }

    @Nonnull
    @Override
    public Condition build() {
        return new ConditionImpl(conditionValues, type, isNegative, range);
    }

    public static @Nonnull ConditionBuilder from(@Nonnull Condition instance) {
        nullThrowIllegalArgument(instance);
        ConditionBuilder builder = new ConditionBuilder(instance.getType());
        builder.conditionValuesSet(instance.getConditionValues())
                .negative(instance.isNegative())
                .range(instance.getRange());

        return builder;
    }
}
