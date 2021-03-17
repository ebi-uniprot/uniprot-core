package org.uniprot.core.taxonomy;

import java.io.Serializable;

public interface TaxonomyInactiveReason extends Serializable {

    TaxonomyInactiveReasonType getInactiveReasonType();

    long getMergedTo();

    boolean hasInactiveReasonType();

    boolean hasMergedTo();
}
