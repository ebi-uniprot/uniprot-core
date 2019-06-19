package uk.ac.ebi.uniprot.cv.keyword;

/**
 * @author lgonzales
 */
public interface KeywordStatistics {

    long getReviewedProteinCount();

    long getUnreviewedProteinCount();

}
