package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.RuleException;

public abstract class AbstractRuleBuilder<S extends AbstractRuleBuilder, T extends Rule<R>, R>
        implements Builder<T> {

    protected List<ConditionSet> conditionSets = new ArrayList<>();
    protected List<Annotation> annotations = new ArrayList<>();
    protected List<RuleException<R>> ruleExceptions = new ArrayList<>();

    public @Nonnull S conditionSetsAdd(ConditionSet conditionSet) {
        addOrIgnoreNull(conditionSet, this.conditionSets);
        return getThis();
    }

    public @Nonnull S conditionSetsSet(List<ConditionSet> conditionSets) {
        this.conditionSets = modifiableList(conditionSets);
        return getThis();
    }

    public @Nonnull S annotationsAdd(Annotation annotation) {
        addOrIgnoreNull(annotation, this.annotations);
        return getThis();
    }

    public @Nonnull S annotationsSet(List<Annotation> annotations) {
        this.annotations = modifiableList(annotations);
        return getThis();
    }

    public @Nonnull S ruleExceptionsAdd(RuleException<R> ruleExeption) {
        addOrIgnoreNull(ruleExeption, this.ruleExceptions);
        return getThis();
    }

    public @Nonnull S ruleExceptionsSet(List<RuleException<R>> ruleExceptions) {
        this.ruleExceptions = modifiableList(ruleExceptions);
        return getThis();
    }

    protected abstract @Nonnull S getThis();
}