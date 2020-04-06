package org.uniprot.core.unirule;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.io.Serializable;
import java.util.List;

public interface RuleException<T> extends Serializable {
    String getNote();
    String getCategory();
    T getAnnotation();// either Annotation or PositionalFeature
    List<UniProtKBAccession> getAccessions();
}
