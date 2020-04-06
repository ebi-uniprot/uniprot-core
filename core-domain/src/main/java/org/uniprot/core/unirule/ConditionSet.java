package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

public interface ConditionSet extends Serializable {
    List<Condition> getConditions();
}
