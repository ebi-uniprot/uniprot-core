package org.uniprot.core.unirule.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.unirule.*;

public class CaseRuleImpl<R extends RuleExceptionAnnotationType> extends RuleImpl<R> implements CaseRule<R> {

    private static final long serialVersionUID = 70596874547936434L;
    private boolean overallStatsExempted;

    CaseRuleImpl() {
        super();
    }

    CaseRuleImpl(
            List<ConditionSet> conditionSets,
            List<Annotation> annotations,
            List<RuleException<R>> ruleExceptions,
            boolean overallStatsExempted) {
        super(conditionSets, annotations, ruleExceptions);
        this.overallStatsExempted = overallStatsExempted;
    }

    @Override
    public boolean isOverallStatsExempted() {
        return overallStatsExempted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaseRuleImpl)) return false;
        if (!super.equals(o)) return false;
        CaseRuleImpl caseRule = (CaseRuleImpl) o;
        return overallStatsExempted == caseRule.overallStatsExempted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), overallStatsExempted);
    }
}
