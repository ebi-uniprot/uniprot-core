package org.uniprot.core.taxonomy;

import java.io.Serializable;

public interface TaxonomyStatistics extends Serializable {

    long getReviewedProteinCount();

    long getUnreviewedProteinCount();

    long getReferenceProteomeCount();

    long getCompleteProteomeCount();

    boolean hasReviewedProteinCount();

    boolean hasUnreviewedProteinCount();

    boolean hasReferenceProteomeCount();

    boolean hasCompleteProteomeCount();
}
