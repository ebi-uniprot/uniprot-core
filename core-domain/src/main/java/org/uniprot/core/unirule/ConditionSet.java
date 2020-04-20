package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

/** @author sahmad */
public interface ConditionSet extends Serializable {
    List<Condition> getConditions();
}
