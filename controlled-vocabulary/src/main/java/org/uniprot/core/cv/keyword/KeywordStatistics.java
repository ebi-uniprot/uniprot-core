package org.uniprot.core.cv.keyword;

/** @author lgonzales */
public interface KeywordStatistics {

    long getReviewedProteinCount();

    long getUnreviewedProteinCount();
}
