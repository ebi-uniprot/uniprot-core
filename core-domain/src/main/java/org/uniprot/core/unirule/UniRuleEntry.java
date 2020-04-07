package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** @author sahmad */
public interface UniRuleEntry extends Serializable {
    UniRuleEntryId getId();

    Information getInformation();

    RuleStatus getRuleStatus();

    UniRule getMainRule();

    List<CaseUniRule> getOtherRules();

    List<SAMFeatureSet> getSamFeatureSets();

    List<PositionFeatureSet> getPositionFeatureSets();

    String getCreator();

    String getModifiedBy();

    Date getCreated();

    Date getModified();
}
