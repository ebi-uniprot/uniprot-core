package org.uniprot.core.unirule.builder;

import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.RuleException;

public abstract class AbstractRuleBuilder<T extends Rule> implements Builder<T> {

    protected List<ConditionSet> conditionSets;
    protected List<Annotation> annotations;
    protected List<RuleException> ruleExceptions;

    public AbstractRuleBuilder(
            List<ConditionSet> conditionSets,
            List<Annotation> annotations,
            List<RuleException> ruleExceptions) {

        this.conditionSets = conditionSets;
        this.annotations = annotations;
        this.ruleExceptions = ruleExceptions;
    }
}
