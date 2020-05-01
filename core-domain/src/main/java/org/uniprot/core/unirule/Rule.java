package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

/** @author sahmad */
public interface Rule<R extends RuleExceptionAnnotationType> {
    List<ConditionSet> getConditionSets();

    List<Annotation> getAnnotations();

    List<RuleException<R>> getRuleExceptions();
}
