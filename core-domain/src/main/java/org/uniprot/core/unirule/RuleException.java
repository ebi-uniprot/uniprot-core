package org.uniprot.core.unirule;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.io.Serializable;
import java.util.List;

/** @author sahmad */
public interface RuleException extends Serializable {
    String getNote();

    String getCategory();

    RuleExceptionAnnotation getAnnotation(); // either Annotation or PositionalFeature

    List<UniProtKBAccession> getAccessions();
}
