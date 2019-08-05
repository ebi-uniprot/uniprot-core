package org.uniprot.core.taxonomy;

public interface TaxonomyInactiveReason {

    TaxonomyInactiveReasonType getInactiveReasonType();

    long getMergedTo();

    boolean hasInactiveReasonType();

    boolean hasMergedTo();

}
