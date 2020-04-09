package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.Date;
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

    String getCreatedBy();

    String getModifiedBy();

    Date getCreatedDate();

    Date getModifiedDate();
}
