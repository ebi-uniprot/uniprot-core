package org.uniprot.core.unirule;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/** @author sahmad */
public interface UniRuleEntry<R extends RuleExceptionAnnotationType> extends Serializable {
    UniRuleId getUniRuleId();

    Information getInformation();

    RuleStatus getRuleStatus();

    Rule<R> getMainRule();

    List<CaseRule<R>> getOtherRules();

    List<SamFeatureSet> getSamFeatureSets();

    List<PositionFeatureSet<R>> getPositionFeatureSets();

    String getCreatedBy();

    String getModifiedBy();

    LocalDate getCreatedDate();

    LocalDate getModifiedDate();
}
