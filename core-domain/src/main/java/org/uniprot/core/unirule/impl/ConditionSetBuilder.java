package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.*;

import org.uniprot.core.Builder;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class ConditionSetBuilder implements Builder<ConditionSet> {

    private List<Condition> conditions;

    public ConditionSetBuilder(Condition condition) {
        this.conditions = new ArrayList<>();
        addOrIgnoreNull(condition, this.conditions);
    }

    public ConditionSetBuilder(List<Condition> conditions) {
        this.conditions = modifiableList(conditions);
    }

    public @Nonnull ConditionSetBuilder conditionsAdd(Condition condition) {
        addOrIgnoreNull(condition, this.conditions);
        return this;
    }

    public @Nonnull ConditionSetBuilder conditionsSet(List<Condition> conditions) {
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
        return new ConditionSetBuilder(instance.getConditions());
    }
}
