package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.Range;

/** @author sahmad */
public interface Condition extends Serializable {
    List<ConditionValue> getConditionValues();

    String getType();

    boolean isNegative();

    Range getRange();

    FeatureTagConditionValue getTag();
}
