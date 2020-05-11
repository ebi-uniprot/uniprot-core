package org.uniprot.core.unirule;

import java.io.Serializable;

import org.uniprot.core.unirule.impl.RuleExceptionAnnotationType;

public interface RuleExceptionAnnotation extends Serializable {
    RuleExceptionAnnotationType getAnnotationType();
}
