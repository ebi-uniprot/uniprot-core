package uk.ac.ebi.uniprot.domain.taxonomy;

public interface TaxonomyInactiveReason {

    TaxonomyInactiveReasonType getInactiveReasonType();

    long getMergedTo();

    boolean hasInactiveReasonType();

    boolean hasMergedTo();

}
