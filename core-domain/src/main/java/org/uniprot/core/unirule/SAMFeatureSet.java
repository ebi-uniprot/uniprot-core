package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

/**
 * @author sahmad
 * SAM - Sequence Analysis Method
 */
public interface SAMFeatureSet extends Serializable {
    ConditionSet getConditionSet();
    List<Annotation> getAnnotations();
    SAMTrigger getSAMTrigger();
}
