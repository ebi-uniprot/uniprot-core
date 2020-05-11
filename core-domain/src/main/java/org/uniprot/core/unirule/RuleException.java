package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

/** @author sahmad */
public interface RuleException extends Serializable {
    String getNote();

    String getCategory();

    RuleExceptionAnnotation getAnnotation(); // either Annotation or PositionalFeature

    List<UniProtKBAccession> getAccessions();
}
