package org.uniprot.core.unirule;

import java.io.Serializable;
import java.util.List;

/** @author sahmad SAM - Sequence Analysis Method */
public interface SamFeatureSet extends Serializable {
    List<Condition> getConditions();

    List<Annotation> getAnnotations();

    SamTrigger getSamTrigger();
}
