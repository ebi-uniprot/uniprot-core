package org.uniprot.core.unirule;

import org.uniprot.core.unirule.impl.RuleExceptionAnnotationType;

import java.io.Serializable;

public interface RuleExceptionAnnotation extends Serializable {
    RuleExceptionAnnotationType getAnnotationType();
}
