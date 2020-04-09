package org.uniprot.core.unirule.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Range;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionValue;
import org.uniprot.core.unirule.impl.ConditionImpl;

public class ConditionBuilder implements Builder<Condition> {
    private List<ConditionValue> conditionValues;
    private String type;
    private boolean isNegative;
    private Range range;

    public ConditionBuilder conditionValuesAdd(ConditionValue conditionValue) {
        addOrIgnoreNull(conditionValue, this.conditionValues);
        return this;
    }

    public ConditionBuilder conditionValuesSet(List<ConditionValue> conditionValues) {
        this.conditionValues = modifiableList(conditionValues);
        return this;
    }

    public ConditionBuilder type(String type) {
        this.type = type;
        return this;
    }

    public ConditionBuilder negative(boolean negative) {
        isNegative = negative;
        return this;
    }

    public ConditionBuilder range(Range range) {
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
        ConditionBuilder builder = new ConditionBuilder();
        builder.conditionValuesSet(instance.getConditionValues())
                .type(instance.getType())
                .negative(instance.isNegative())
                .range(instance.getRange());

        return builder;
    }
}
