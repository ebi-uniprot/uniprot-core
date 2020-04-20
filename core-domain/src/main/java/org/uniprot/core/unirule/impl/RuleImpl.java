package org.uniprot.core.unirule.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.util.Utils;

public class RuleImpl<R> implements Rule<R> {
    private static final long serialVersionUID = 5713754535369572219L;
    private List<ConditionSet> conditionSets;

    private List<Annotation> annotations;

    private List<RuleException<R>> ruleExceptions;

    RuleImpl() {
        this.conditionSets = Collections.emptyList();
        this.annotations = Collections.emptyList();
        this.ruleExceptions = Collections.emptyList();
    }

    RuleImpl(
            List<ConditionSet> conditionSets,
            List<Annotation> annotations,
            List<RuleException<R>> ruleExceptions) {
        this.conditionSets = Utils.unmodifiableList(conditionSets);
        this.annotations = Utils.unmodifiableList(annotations);
        this.ruleExceptions = Utils.unmodifiableList(ruleExceptions);
    }

    @Override
    public List<ConditionSet> getConditionSets() {
        return conditionSets;
    }

    @Override
    public List<Annotation> getAnnotations() {
        return annotations;
    }

    @Override
    public List<RuleException<R>> getRuleExceptions() {
        return ruleExceptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleImpl rule = (RuleImpl) o;
        return Objects.equals(conditionSets, rule.conditionSets)
                && Objects.equals(annotations, rule.annotations)
                && Objects.equals(ruleExceptions, rule.ruleExceptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionSets, annotations, ruleExceptions);
    }
}
