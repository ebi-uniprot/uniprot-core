package org.uniprot.core.unirule;

import org.uniprot.core.Range;

import java.io.Serializable;
import java.util.List;

/** @author sahmad */
public interface Condition extends Serializable {
    List<ConditionValue> getConditionValues();

    String getType();

    boolean isNegative();

    Range getRange();

    FeatureTagConditionValue getTag();
}
