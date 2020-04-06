package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

public interface UniRule extends Serializable {
    List<ConditionSet> getConditionSets();
    List<Annotation> getAnnotations();
    List<RuleException> getRuleExceptions();
}
