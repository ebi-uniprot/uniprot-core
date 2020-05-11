package org.uniprot.core.unirule.impl;

import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ConditionSetImpl implements ConditionSet {

    private static final long serialVersionUID = -5929730192863024936L;
    private List<Condition> conditions;

    ConditionSetImpl() {
        this.conditions = Collections.emptyList();
    }

    ConditionSetImpl(List<Condition> conditions) {
        if (Utils.nullOrEmpty(conditions)) {
            throw new IllegalArgumentException(
                    "conditions is a mandatory parameter for a ConditionSet entry.");
        }
        this.conditions = Utils.unmodifiableList(conditions);
    }

    @Override
    public List<Condition> getConditions() {
        return conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConditionSetImpl)) return false;
        ConditionSetImpl that = (ConditionSetImpl) o;
        return Objects.equals(conditions, that.conditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditions);
    }
}
