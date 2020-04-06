package org.uniprot.core.unirule;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.util.List;

public interface PositionFeatureSet {
    ConditionSet getConditionSet();
    List<Annotation> getAnnotations();
    List<PositionalFeature> getPositionalFeatures();
    List<RuleException> getRuleExceptions();
    UniProtKBAccession getUniProtKBAccession();
    String getAlignmentSignature();
    String getTag();
}
