package org.uniprot.core.unirule.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.impl.ConditionSetImpl;

public class ConditionSetBuilder implements Builder<ConditionSet> {

    private List<Condition> conditions;

    public ConditionSetBuilder conditionsAdd(Condition condition) {
        addOrIgnoreNull(condition, this.conditions);
        return this;
    }

    public ConditionSetBuilder conditionsSet(List<Condition> conditions) {
        this.conditions = modifiableList(conditions);
        return this;
    }

    @Nonnull
    @Override
    public ConditionSet build() {
        return new ConditionSetImpl(conditions);
    }

    public static @Nonnull ConditionSetBuilder from(@Nonnull ConditionSet instance) {
        nullThrowIllegalArgument(instance);
        return new ConditionSetBuilder().conditionsSet(instance.getConditions());
    }
}
