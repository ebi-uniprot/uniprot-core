package org.uniprot.core.unirule;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.uniprot.core.Statistics;

/** @author sahmad */
public interface UniRuleEntry extends Serializable {
    UniRuleId getUniRuleId();

    Information getInformation();

    Rule getMainRule();

    List<CaseRule> getOtherRules();

    List<SamFeatureSet> getSamFeatureSets();

    List<PositionFeatureSet> getPositionFeatureSets();

    Statistics getStatistics();

    LocalDate getCreatedDate();

    LocalDate getModifiedDate();
}
