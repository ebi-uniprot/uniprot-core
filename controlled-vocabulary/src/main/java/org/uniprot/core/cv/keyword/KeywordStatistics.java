package org.uniprot.core.cv.keyword;

import java.io.Serializable;

/** @author lgonzales */
public interface KeywordStatistics extends Serializable {

    long getReviewedProteinCount();

    long getUnreviewedProteinCount();
}
