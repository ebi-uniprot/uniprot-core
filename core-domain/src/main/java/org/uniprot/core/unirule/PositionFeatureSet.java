package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

/** @author sahmad */
public interface PositionFeatureSet<T extends RuleExceptionAnnotationType> extends Serializable {
    List<Condition> getConditions();

    List<Annotation> getAnnotations();

    List<PositionalFeature> getPositionalFeatures();

    List<RuleException<T>> getRuleExceptions();

    UniProtKBAccession getUniProtKBAccession();

    String getAlignmentSignature();

    String getTag();
}
