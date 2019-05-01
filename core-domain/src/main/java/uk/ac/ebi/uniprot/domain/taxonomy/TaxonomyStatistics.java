package uk.ac.ebi.uniprot.domain.taxonomy;

import java.io.Serializable;

public interface TaxonomyStatistics extends Serializable {

    long getReviewedProteinCount();

    long getUnreviewedProteinCount();

    long getProteomeCount();

    boolean hasReviewedProteinCount();

    boolean hasUnreviewedProteinCount();

    boolean hasProteomeCount();

}
