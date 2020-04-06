package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

public interface Condition extends Serializable {
    List<ConditionValue> getConditionValues();
    String getType();
    boolean isNegative();
    String getStart();
    String getEnd();
}
