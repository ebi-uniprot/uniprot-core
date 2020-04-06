package org.uniprot.core.unirule;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import java.io.Serializable;
import java.util.List;
/**
 * @author sahmad
 */
public interface UniRuleEntry extends Serializable {
    UniRuleEntryId getId();
    int getAnnotatedProteinCount();
    List<Taxonomy> getTaxonomicScopes();
    List<UniProtKBId> getUniProtIds();
    List<UniProtKBAccession> getUniProtAccessions();
    RuleStatus getRuleStatus();
    UniRule getMainRule();
    List<CaseUniRule> getMiniRules();
    List<SAMFeatureSet> getSamFeatureSets();
    List<PositionFeatureSet> getPositionFeatureSets();
}