package org.uniprot.core.unirule;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.io.Serializable;
import java.util.List;

public interface PositionFeatureSet extends Serializable {
    List<Condition> getConditions();
    List<Annotation> getAnnotations();
    List<PositionalFeature> getPositionalFeatures();
    List<RuleException> getRuleExceptions();
    UniProtKBAccession getUniProtKBAccession();
    String getAlignmentSignature();
    String getTag();
}
