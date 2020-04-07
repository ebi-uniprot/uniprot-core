package org.uniprot.core.unirule;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;

import java.io.Serializable;
import java.util.List;
/**
 * @author sahmad
 */
public interface UniRuleEntry extends Serializable {
    UniRuleEntryId getId();
    List<UniProtKBId> getUniProtIds();
    List<UniProtKBAccession> getUniProtAccessions();
    RuleStatus getRuleStatus();
    UniRule getMainRule();
    List<CaseUniRule> getOtherRules();
    List<SAMFeatureSet> getSamFeatureSets();
    List<PositionFeatureSet> getPositionFeatureSets();
}