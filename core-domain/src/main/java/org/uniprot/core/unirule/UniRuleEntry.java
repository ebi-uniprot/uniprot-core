package org.uniprot.core.unirule;

import org.uniprot.core.Statistics;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/** @author sahmad */
public interface UniRuleEntry extends Serializable {
    UniRuleId getUniRuleId();

    Information getInformation();

    RuleStatus getRuleStatus();

    Rule getMainRule();

    List<CaseRule> getOtherRules();

    List<SamFeatureSet> getSamFeatureSets();

    List<PositionFeatureSet> getPositionFeatureSets();

    Statistics getStatistics();

    String getCreatedBy();

    String getModifiedBy();

    LocalDate getCreatedDate();

    LocalDate getModifiedDate();
}
